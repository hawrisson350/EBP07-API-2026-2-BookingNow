package co.edu.udea.bookingnow.application.port.in.negocio;

import co.edu.udea.bookingnow.domain.model.HorarioAtencion;
import java.util.List;

public interface ListarHorariosUseCase { List<HorarioAtencion> listar(Long idProveedor,Long idNegocio); }
