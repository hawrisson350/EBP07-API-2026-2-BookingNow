package co.edu.udea.bookingnow.application.port.in.empleado;

import co.edu.udea.bookingnow.domain.model.Empleado;
import java.util.List;

public interface ListarEmpleadosUseCase { List<Empleado> listar(Long idProveedor, Long idNegocio); }
