package co.edu.udea.bookingnow.application.port.out;

import co.edu.udea.bookingnow.domain.model.ReporteArchivo;
import co.edu.udea.bookingnow.domain.model.ReporteReservaFila;
import java.time.LocalDate;
import java.util.List;

public interface GeneradorReportePort {
    ReporteArchivo generar(String tipoReporte, String formato, LocalDate desde, LocalDate hasta,
            List<ReporteReservaFila> filas);
}
