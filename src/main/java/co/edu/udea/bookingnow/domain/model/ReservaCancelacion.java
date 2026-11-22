package co.edu.udea.bookingnow.domain.model;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservaCancelacion(Long idReserva, Long idCliente, Long idProveedor,
        String correoCliente, String nombreCliente, String nombreServicio, String nombreNegocio,
        LocalDate fecha, LocalTime hora, String estado) {
}
