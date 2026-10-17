package co.edu.udea.bookingnow.application.port.in.servicio;

import co.edu.udea.bookingnow.domain.model.AsignacionServicio;

public interface AsignarPersonalYRecursosUseCase { AsignacionServicio asignar(Long idProveedor,Long idNegocio,Long idServicio,AsignarPersonalYRecursosCommand command); }
