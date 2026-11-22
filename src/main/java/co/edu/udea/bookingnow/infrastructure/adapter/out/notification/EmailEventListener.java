package co.edu.udea.bookingnow.infrastructure.adapter.out.notification;

import co.edu.udea.bookingnow.application.port.out.EnviarEmailPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class EmailEventListener {
    private static final Logger log = LoggerFactory.getLogger(EmailEventListener.class);
    private final EnviarEmailPort email;

    public EmailEventListener(EnviarEmailPort email) {
        this.email = email;
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void enviar(EmailEvent evento) {
        try {
            email.enviar(evento.destinatario(), evento.asunto(), evento.contenido());
        } catch (Exception e) {
            log.error("booking_email result=fallo destinatario_hash={}", Integer.toHexString(evento.destinatario().hashCode()), e);
        }
    }
}
