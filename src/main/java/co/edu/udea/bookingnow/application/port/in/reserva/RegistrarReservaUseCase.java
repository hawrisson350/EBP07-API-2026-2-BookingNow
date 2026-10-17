package co.edu.udea.bookingnow.application.port.in.reserva;

import co.edu.udea.bookingnow.domain.model.Reserva;

public interface RegistrarReservaUseCase { Reserva registrar(Long idCliente,RegistrarReservaCommand command); }
