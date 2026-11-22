package co.edu.udea.bookingnow.application.port.in.reserva;

import co.edu.udea.bookingnow.domain.model.Reserva;

public interface CancelarReservaUseCase {
    Reserva cancelar(Long idReserva, Long idActor, String rolActor, String motivo);
}
