package co.edu.udea.bookingnow.infrastructure.adapter.out.notification;

import co.edu.udea.bookingnow.application.port.in.reserva.ProcesarRecordatoriosUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnExpression("'${bookingnow.resend.api-key:}' != '' and '${bookingnow.resend.from:}' != ''")
public class RecordatorioReservasScheduler {
    private static final Logger log = LoggerFactory.getLogger(RecordatorioReservasScheduler.class);
    private final ProcesarRecordatoriosUseCase procesar;

    public RecordatorioReservasScheduler(ProcesarRecordatoriosUseCase procesar) {
        this.procesar = procesar;
    }

    @Scheduled(fixedDelayString = "${bookingnow.reminders.interval-ms:300000}")
    public void ejecutar() {
        int cantidad = procesar.procesar();
        if (cantidad > 0) log.info("booking_reminders result=procesados cantidad={}", cantidad);
    }
}
