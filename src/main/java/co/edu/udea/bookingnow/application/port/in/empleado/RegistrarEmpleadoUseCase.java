package co.edu.udea.bookingnow.application.port.in.empleado;

import co.edu.udea.bookingnow.domain.model.Empleado;

public interface RegistrarEmpleadoUseCase { Empleado registrar(Long idProveedor, Long idNegocio, RegistrarEmpleadoCommand command); }
