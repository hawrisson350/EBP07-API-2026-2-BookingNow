package co.edu.udea.bookingnow.infrastructure.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.WriteListener;
import org.springframework.web.util.ContentCachingRequestWrapper;
import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.io.IOException;
import java.util.UUID;
import java.util.Map;
import java.util.LinkedHashMap;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/** Registra metadatos y capturas acotadas para diagnosticar reportes y errores. */
@Component
public class RequestLogFilter extends OncePerRequestFilter {
    private static final Logger log = LoggerFactory.getLogger(RequestLogFilter.class);
    private static final int MAX_CAPTURE_BYTES = 16 * 1024;
    private static final ObjectMapper JSON = new ObjectMapper();
    private final LocalAuditLog audit;

    public RequestLogFilter(LocalAuditLog audit) { this.audit = audit; }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        return !path.startsWith("/api/") && !path.equals("/health");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        String provided = request.getHeader("X-Request-Id");
        String requestId = provided != null && provided.matches("[A-Za-z0-9-]{1,80}") ? provided : UUID.randomUUID().toString();
        long started = System.nanoTime();
        boolean reportRequest = request.getRequestURI().equals("/api/reportes")
                && "POST".equalsIgnoreCase(request.getMethod());
        ContentCachingRequestWrapper requestCapture = reportRequest
                ? new ContentCachingRequestWrapper(request, MAX_CAPTURE_BYTES) {
                    @Override protected void handleContentOverflow(int contentCacheLimit) { }
                }
                : null;
        CappedResponseCapture responseCapture = new CappedResponseCapture(response, MAX_CAPTURE_BYTES);
        MDC.put("requestId", requestId);
        response.setHeader("X-Request-Id", requestId);
        Throwable[] failure = new Throwable[1];
        try {
            chain.doFilter(requestCapture == null ? request : requestCapture, responseCapture);
        } catch (IOException | ServletException | RuntimeException | Error ex) {
            failure[0] = ex;
            throw ex;
        } finally {
            long durationMs = (System.nanoTime() - started) / 1_000_000;
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String actor = auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getName()) ? auth.getName() : "ANONIMO";
            String remoteIp = request.getRemoteAddr();
            String forwarded = request.getHeader("X-Forwarded-For");
            Map<String, Object> event = new LinkedHashMap<>();
            event.put("requestId", requestId);
            event.put("method", request.getMethod());
            event.put("path", request.getRequestURI());
            int effectiveStatus = failure[0] != null && response.getStatus() < 400 ? 500 : response.getStatus();
            event.put("status", effectiveStatus);
            event.put("durationMs", durationMs);
            event.put("actor", actor);
            event.put("remoteIp", remoteIp);
            event.put("forwardedFor", forwarded == null ? "" : forwarded);
            event.put("userAgent", request.getHeader("User-Agent") == null ? "" : request.getHeader("User-Agent"));
            if (requestCapture != null) {
                String body = safeReportRequest(requestCapture.getContentAsByteArray(), request.getCharacterEncoding());
                if (!body.isBlank()) event.put("requestBody", body);
            }
            String responseType = response.getContentType() == null ? "" : response.getContentType();
            if (effectiveStatus >= 400 && isText(responseType)) {
                event.put("responseContentType", responseType);
                event.put("responseBody", responseCapture.body(response.getCharacterEncoding()));
                event.put("responseTruncated", responseCapture.truncated());
            }
            if (failure[0] != null) {
                Throwable root = failure[0];
                while (root.getCause() != null && root.getCause() != root) root = root.getCause();
                event.put("exceptionType", root.getClass().getSimpleName());
                String message = root.getMessage();
                if (message != null && !message.isBlank()) {
                    message = message.replaceAll("(?i)(password|contrasena|token|secret)\\s*[:=]\\s*[^,\\s]+", "$1=[redactado]");
                    message = message.replaceAll("(?i)Bearer\\s+[A-Za-z0-9._~-]+", "Bearer [redactado]");
                    event.put("exceptionMessage", message.substring(0, Math.min(message.length(), 1200)));
                }
            }
            audit.append("requests.jsonl", event);
            log.info("http_request method={} path={} status={} duration_ms={}",
                    request.getMethod(), request.getRequestURI(), response.getStatus(), durationMs);
            MDC.remove("requestId");
        }
    }

    private static boolean isText(String contentType) {
        String type = contentType.toLowerCase(java.util.Locale.ROOT);
        return type.contains("json") || type.startsWith("text/");
    }

    private static String safeReportRequest(byte[] bytes, String encoding) {
        if (bytes.length == 0) return "";
        try {
            var tree = JSON.readTree(new String(bytes, charset(encoding)));
            redact(tree);
            return JSON.writeValueAsString(tree);
        } catch (Exception ignored) {
            return "[cuerpo JSON omitido: no se pudo interpretar con seguridad]";
        }
    }

    private static void redact(com.fasterxml.jackson.databind.JsonNode node) {
        if (node instanceof com.fasterxml.jackson.databind.node.ObjectNode object) {
            var fields = object.fields();
            while (fields.hasNext()) {
                var field = fields.next();
                String key = field.getKey().toLowerCase(java.util.Locale.ROOT);
                if (key.contains("password") || key.contains("contrasena") || key.contains("token")
                        || key.contains("secret") || key.contains("authorization") || key.contains("base64")
                        || key.contains("imagen") || key.contains("foto")) {
                    object.put(field.getKey(), "[redactado]");
                } else {
                    redact(field.getValue());
                }
            }
        } else if (node instanceof com.fasterxml.jackson.databind.node.ArrayNode array) {
            array.forEach(RequestLogFilter::redact);
        }
    }

    private static Charset charset(String encoding) {
        try { return encoding == null ? StandardCharsets.UTF_8 : Charset.forName(encoding); }
        catch (Exception ignored) { return StandardCharsets.UTF_8; }
    }

    /** Captures a bounded prefix while streaming bytes normally to the client. */
    private static final class CappedResponseCapture extends HttpServletResponseWrapper {
        private final ByteArrayOutputStream captured = new ByteArrayOutputStream();
        private final int maxBytes;
        private ServletOutputStream output;
        private PrintWriter writer;
        private boolean truncated;

        CappedResponseCapture(HttpServletResponse response, int maxBytes) {
            super(response);
            this.maxBytes = maxBytes;
        }

        @Override public ServletOutputStream getOutputStream() throws java.io.IOException {
            if (writer != null) throw new IllegalStateException("getWriter() ya fue llamado");
            if (output == null) {
                ServletOutputStream delegate = getResponse().getOutputStream();
                output = new ServletOutputStream() {
                    @Override public boolean isReady() { return delegate.isReady(); }
                    @Override public void setWriteListener(WriteListener listener) { delegate.setWriteListener(listener); }
                    @Override public void write(int b) throws java.io.IOException {
                        delegate.write(b);
                        if (captured.size() < CappedResponseCapture.this.maxBytes) captured.write(b);
                        else truncated = true;
                    }
                    @Override public void write(byte[] b, int off, int len) throws java.io.IOException {
                        delegate.write(b, off, len);
                        int remaining = CappedResponseCapture.this.maxBytes - captured.size();
                        if (remaining > 0) captured.write(b, off, Math.min(remaining, len));
                        if (len > remaining) truncated = true;
                    }
                };
            }
            return output;
        }

        @Override public PrintWriter getWriter() throws java.io.IOException {
            if (output != null) throw new IllegalStateException("getOutputStream() ya fue llamado");
            if (writer == null) writer = new PrintWriter(new OutputStreamWriter(getOutputStream(), charset(getCharacterEncoding())));
            return writer;
        }

        String body(String encoding) {
            if (writer != null) writer.flush();
            return new String(captured.toByteArray(), charset(encoding));
        }

        boolean truncated() { return truncated; }
    }

}
