package co.edu.udea.bookingnow.application.port.out;

import co.edu.udea.bookingnow.domain.model.AsignacionServicio;
import java.util.Optional;

public interface AsignacionServicioRepositoryPort {
 Optional<AsignacionServicio> obtener(Long idServicio);
 AsignacionServicio reemplazar(AsignacionServicio asignacion);
}
