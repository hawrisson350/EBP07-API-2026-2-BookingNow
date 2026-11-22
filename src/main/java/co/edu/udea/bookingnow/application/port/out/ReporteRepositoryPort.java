package co.edu.udea.bookingnow.application.port.out;

import co.edu.udea.bookingnow.domain.model.ReporteReservaFila;
import java.time.LocalDate;
import java.util.List;

public interface ReporteRepositoryPort {
    List<ReporteReservaFila> consultarReservas(Long idProveedor, Long idNegocio, Long idServicio,
            Long idRecurso, String estado, LocalDate desde, LocalDate hasta);
}
