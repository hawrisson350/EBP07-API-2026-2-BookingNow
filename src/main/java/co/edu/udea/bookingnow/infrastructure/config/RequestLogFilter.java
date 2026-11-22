package co.edu.udea.bookingnow.infrastructure.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;
import java.util.Map;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/** Registra metadatos HTTP sin registrar cuerpos, contraseñas ni tokens. */
@Component
public class RequestLogFilter extends OncePerRequestFilter {
    private static final Logger log = LoggerFactory.getLogger(RequestLogFilter.class);
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
        MDC.put("requestId", requestId);
        response.setHeader("X-Request-Id", requestId);
        try {
            chain.doFilter(request, response);
        } finally {
            long durationMs = (System.nanoTime() - started) / 1_000_000;
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String actor = auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getName()) ? auth.getName() : "ANONIMO";
            String remoteIp = request.getRemoteAddr();
            String forwarded = request.getHeader("X-Forwarded-For");
            Map<String, Object> event = Map.of("requestId", requestId, "method", request.getMethod(),
                    "path", request.getRequestURI(), "status", response.getStatus(), "durationMs", durationMs,
                    "actor", actor, "remoteIp", remoteIp, "forwardedFor", forwarded == null ? "" : forwarded,
                    "userAgent", request.getHeader("User-Agent") == null ? "" : request.getHeader("User-Agent"));
            audit.append("requests.jsonl", event);
            log.info("http_request method={} path={} status={} duration_ms={}",
                    request.getMethod(), request.getRequestURI(), response.getStatus(), durationMs);
            MDC.remove("requestId");
        }
    }

}
