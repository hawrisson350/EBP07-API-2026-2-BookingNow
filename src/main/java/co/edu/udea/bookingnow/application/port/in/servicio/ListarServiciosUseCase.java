package co.edu.udea.bookingnow.application.port.in.servicio;
import co.edu.udea.bookingnow.domain.model.Servicio;
import java.util.List;
public interface ListarServiciosUseCase {
    List<Servicio> listar(Long idProveedor, Long idNegocio);
    List<Servicio> listarParaAdministrador(Long idNegocio);
}
