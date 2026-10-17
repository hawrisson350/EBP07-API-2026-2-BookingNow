package co.edu.udea.bookingnow.application.port.out;

import co.edu.udea.bookingnow.domain.model.Recurso;
import java.util.List;

public interface RecursoRepositoryPort {
    Recurso guardar(Recurso recurso);
    List<Recurso> listarPorNegocio(Long idNegocio);
    java.util.Set<Long> idsDisponiblesPorNegocio(Long idNegocio, java.util.Set<Long> ids);
}
