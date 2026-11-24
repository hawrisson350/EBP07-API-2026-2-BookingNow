package co.edu.udea.bookingnow.infrastructure.adapter.out.notification;

import co.edu.udea.bookingnow.application.port.out.EnviarEmailPort;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "bookingnow.email.provider", havingValue = "gmail")
public class GmailSmtpEmailAdapter implements EnviarEmailPort {
    private static final Logger log = LoggerFactory.getLogger(GmailSmtpEmailAdapter.class);
    private final JavaMailSender mailSender;
    private final String username;
    private final String from;

    public GmailSmtpEmailAdapter(JavaMailSender mailSender,
            @Value("${spring.mail.username:}") String username,
            @Value("${bookingnow.smtp.from:}") String from) {
        this.mailSender = mailSender;
        this.username = username;
        this.from = from;
    }

    @Override
    public void enviar(String destinatario, String asunto, String contenido) {
        if (username.isBlank() || from.isBlank()) {
            log.warn("booking_email provider=gmail result=no_configurado");
            return;
        }
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");
            helper.setFrom(from, "BookingNow");
            helper.setTo(destinatario);
            helper.setSubject(asunto);
            helper.setText(contenido, html(asunto, contenido));
            mailSender.send(message);
            log.info("booking_email provider=gmail result=enviado");
        } catch (Exception ex) {
            throw new IllegalStateException("No fue posible enviar la notificación por Gmail SMTP", ex);
        }
    }

    private String html(String subject, String content) {
        String body = escape(content).replace("\r\n", "\n").replace("\n", "<br>");
        return """
                <!doctype html><html lang="es"><body style="margin:0;background:#f3f4f6;font-family:Arial,sans-serif;color:#202124">
                  <div style="max-width:600px;margin:32px auto;background:#fff;border:1px solid #e5e7eb;border-radius:12px;overflow:hidden">
                    <div style="padding:22px 28px;background:#171717;color:#fff;border-bottom:4px solid #ff8a24">
                      <div style="font-size:12px;letter-spacing:2px;color:#ffb36c">BOOKINGNOW</div>
                      <div style="font-size:22px;font-weight:700;margin-top:8px">Notificación de reserva</div>
                    </div>
                    <div style="padding:28px;font-size:15px;line-height:1.7">
                      <h1 style="font-size:20px;margin:0 0 16px;color:#202124">%s</h1>
                      <p style="margin:0">%s</p>
                    </div>
                    <div style="padding:16px 28px;background:#f9fafb;color:#6b7280;font-size:12px">Mensaje automático de BookingNow.</div>
                  </div>
                </body></html>
                """.formatted(escape(subject), body);
    }

    private String escape(String value) {
        return value == null ? "" : value.replace("&", "&amp;").replace("<", "&lt;")
                .replace(">", "&gt;").replace("\\\"", "&quot;").replace("'", "&#39;");
    }
}
