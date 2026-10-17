package co.edu.udea.bookingnow.application.port.in.recurso;

import co.edu.udea.bookingnow.domain.model.Recurso;
import java.util.List;

public interface ListarRecursosUseCase { List<Recurso> listar(Long idProveedor, Long idNegocio); }
