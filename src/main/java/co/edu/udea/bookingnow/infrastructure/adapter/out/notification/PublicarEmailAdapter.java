package co.edu.udea.bookingnow.infrastructure.adapter.out.notification;

import co.edu.udea.bookingnow.application.port.out.PublicarEmailPort;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class PublicarEmailAdapter implements PublicarEmailPort {
    private final ApplicationEventPublisher eventos;

    public PublicarEmailAdapter(ApplicationEventPublisher eventos) {
        this.eventos = eventos;
    }

    @Override
    public void publicar(String destinatario, String asunto, String contenido) {
        eventos.publishEvent(new EmailEvent(destinatario, asunto, contenido));
    }
}
