package co.edu.udea.bookingnow.application.port.in.negocio;

import co.edu.udea.bookingnow.domain.model.HorarioAtencion;
import java.util.List;

public interface ConfigurarHorariosUseCase { List<HorarioAtencion> configurar(Long idProveedor,Long idNegocio,List<HorarioAtencionCommand> horarios); }
