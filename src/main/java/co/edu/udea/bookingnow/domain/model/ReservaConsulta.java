package co.edu.udea.bookingnow.domain.model;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservaConsulta(
        Long idReserva,
        LocalDate fecha,
        LocalTime hora,
        String nombreServicio,
        String nombreNegocio,
        String estado) {
}
