package co.edu.udea.bookingnow.application.port.out;

import co.edu.udea.bookingnow.domain.model.Empleado;
import java.util.List;

public interface EmpleadoRepositoryPort {
    Empleado guardar(Empleado empleado);
    List<Empleado> listarPorNegocio(Long idNegocio);
    boolean existeDocumentoEnNegocio(Long idNegocio, String documento);
    java.util.Set<Long> idsPorNegocio(Long idNegocio, java.util.Set<Long> ids);
}
