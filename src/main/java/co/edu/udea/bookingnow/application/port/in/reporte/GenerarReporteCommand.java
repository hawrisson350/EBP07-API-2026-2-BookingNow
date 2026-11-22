package co.edu.udea.bookingnow.application.port.in.reporte;

import java.time.LocalDate;

public record GenerarReporteCommand(String tipoReporte, LocalDate fechaDesde, LocalDate fechaHasta,
        Long idNegocio, Long idServicio, Long idRecurso, String estado, String formato) {
}
