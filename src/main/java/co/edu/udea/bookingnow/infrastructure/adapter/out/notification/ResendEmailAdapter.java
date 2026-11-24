package co.edu.udea.bookingnow.infrastructure.adapter.out.notification;

import co.edu.udea.bookingnow.application.port.out.EnviarEmailPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;

@Component
@ConditionalOnProperty(name = "bookingnow.email.provider", havingValue = "resend", matchIfMissing = true)
public class ResendEmailAdapter implements EnviarEmailPort {
    private static final Logger log = LoggerFactory.getLogger(ResendEmailAdapter.class);
    private final String apiKey;
    private final String from;
    private final RestClient client;

    public ResendEmailAdapter(@Value("${bookingnow.resend.api-key:}") String apiKey,
                              @Value("${bookingnow.resend.from:}") String from,
                              @Value("${bookingnow.resend.base-url:https://api.resend.com}") String baseUrl) {
        this.apiKey = apiKey;
        this.from = from;
        this.client = RestClient.builder().baseUrl(baseUrl).build();
    }

    @Override
    public void enviar(String destinatario, String asunto, String contenido) {
        if (apiKey.isBlank() || from.isBlank()) {
            log.warn("booking_email result=no_configurado destinatario_hash={}",
                    Integer.toHexString(destinatario.hashCode()));
            return;
        }

        Map<String, Object> body = Map.of(
                "from", from,
                "to", List.of(destinatario),
                "subject", asunto,
                "text", contenido);

        client.post()
                .uri("/emails")
                .header("Authorization", "Bearer " + apiKey)
                .contentType(MediaType.APPLICATION_JSON)
                .body(body)
                .retrieve()
                .toBodilessEntity();
    }
}
