package co.edu.udea.bookingnow.application.port.out;
import co.edu.udea.bookingnow.domain.model.Servicio;
import java.util.List;
import java.util.Optional;
import co.edu.udea.bookingnow.application.port.in.servicio.PaginaServicios;
public interface ServicioRepositoryPort {
    Servicio guardar(Servicio servicio);
    List<Servicio> listarPorNegocio(Long idNegocio);
    Optional<Servicio> obtenerPorId(Long idServicio);
    PaginaServicios buscar(String nombre, String tipo, int pagina, int tamano);
}
