package co.edu.udea.bookingnow.infrastructure.adapter.in.rest.dto;

import co.edu.udea.bookingnow.domain.model.ReservaConsulta;
import java.time.LocalDate;
import java.time.LocalTime;

public record ReservaConsultaResponse(
        Long idReserva,
        LocalDate fecha,
        LocalTime hora,
        String nombreServicio,
        String nombreNegocio,
        String estado) {

    public static ReservaConsultaResponse from(ReservaConsulta reserva) {
        return new ReservaConsultaResponse(reserva.idReserva(), reserva.fecha(), reserva.hora(),
                reserva.nombreServicio(), reserva.nombreNegocio(), reserva.estado());
    }
}
