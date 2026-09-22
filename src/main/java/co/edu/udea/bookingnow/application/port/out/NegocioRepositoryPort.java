package co.edu.udea.bookingnow.application.port.out;
import co.edu.udea.bookingnow.domain.model.Negocio;
import java.util.List;
import java.util.Optional;
public interface NegocioRepositoryPort {
    Negocio guardar(Negocio negocio);
    List<Negocio> obtenerTodos();
    List<Negocio> obtenerPorProveedor(Long idProveedor);
    Optional<Negocio> obtenerPorId(Long idNegocio);
}
