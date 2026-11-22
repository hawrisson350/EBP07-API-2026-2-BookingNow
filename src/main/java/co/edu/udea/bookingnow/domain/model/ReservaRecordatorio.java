package co.edu.udea.bookingnow.domain.model;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservaRecordatorio(Long idReserva, String correoCliente, String nombreCliente,
        String nombreServicio, String nombreNegocio, LocalDate fecha, LocalTime hora) {
}
