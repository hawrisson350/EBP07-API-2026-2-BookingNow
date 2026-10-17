package co.edu.udea.bookingnow.application.port.out;

import co.edu.udea.bookingnow.domain.model.HorarioAtencion;
import java.util.List;

public interface HorarioAtencionRepositoryPort {
 List<HorarioAtencion> reemplazarHorarios(Long idNegocio,List<HorarioAtencion> horarios);
 List<HorarioAtencion> listarPorNegocio(Long idNegocio);
}
