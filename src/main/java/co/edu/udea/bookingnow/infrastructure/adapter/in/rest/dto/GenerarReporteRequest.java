package co.edu.udea.bookingnow.infrastructure.adapter.in.rest.dto;

import co.edu.udea.bookingnow.application.port.in.reporte.GenerarReporteCommand;
import java.time.LocalDate;

public record GenerarReporteRequest(String tipoReporte, LocalDate fechaDesde, LocalDate fechaHasta,
        Long idNegocio, Long idServicio, Long idRecurso, String estado, String formato) {
    public GenerarReporteCommand toCommand() {
        return new GenerarReporteCommand(tipoReporte, fechaDesde, fechaHasta, idNegocio,
                idServicio, idRecurso, estado, formato);
    }
}
