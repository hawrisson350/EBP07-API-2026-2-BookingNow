package co.edu.udea.bookingnow.application.service;

import co.edu.udea.bookingnow.application.port.in.reserva.ProcesarRecordatoriosUseCase;
import co.edu.udea.bookingnow.application.port.out.PublicarEmailPort;
import co.edu.udea.bookingnow.application.port.out.ReservaRepositoryPort;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.Instant;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProcesarRecordatoriosService implements ProcesarRecordatoriosUseCase {
    private static final ZoneId ZONA_PROYECTO = ZoneId.of("America/Bogota");
    private final ReservaRepositoryPort reservas;
    private final PublicarEmailPort email;

    public ProcesarRecordatoriosService(ReservaRepositoryPort reservas, PublicarEmailPort email) {
        this.reservas = reservas;
        this.email = email;
    }

    @Override
    @Transactional
    public int procesar() {
        LocalDateTime ahora = LocalDateTime.now(ZONA_PROYECTO);
        LocalDateTime limite = ahora.plusHours(24);
        int procesadas = 0;
        for (var reserva : reservas.buscarRecordatoriosPendientes(limite)) {
            if (reservas.marcarRecordatorioProcesado(reserva.idReserva(), Instant.now())) {
                String asunto = "Recordatorio de tu reserva";
                String contenido = "Hola " + reserva.nombreCliente() + ", te recordamos tu reserva de "
                        + reserva.nombreServicio() + " en " + reserva.nombreNegocio() + " para el "
                        + reserva.fecha() + " a las " + reserva.hora() + ".";
                email.publicar(reserva.correoCliente(), asunto, contenido);
                procesadas++;
            }
        }
        return procesadas;
    }
}
