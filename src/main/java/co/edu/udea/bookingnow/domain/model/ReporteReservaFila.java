package co.edu.udea.bookingnow.domain.model;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReporteReservaFila(Long idReserva, LocalDate fecha, LocalTime hora,
        String nombreCliente, String nombreServicio, String nombreRecurso,
        String nombreNegocio, String estado) {
}
