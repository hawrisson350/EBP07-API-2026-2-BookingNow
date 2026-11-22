package co.edu.udea.bookingnow.application.port.in.reporte;

import co.edu.udea.bookingnow.domain.model.ReporteArchivo;

public interface GenerarReporteUseCase {
    ReporteArchivo generar(Long idActor, String rol, GenerarReporteCommand command);
}
