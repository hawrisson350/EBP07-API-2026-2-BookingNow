package co.edu.udea.bookingnow.application.port.out;
import co.edu.udea.bookingnow.domain.model.Servicio;
import java.util.List;
public interface ServicioRepositoryPort {
    Servicio guardar(Servicio servicio);
    List<Servicio> listarPorNegocio(Long idNegocio);
}
