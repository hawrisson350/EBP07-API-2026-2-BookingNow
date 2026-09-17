package co.edu.udea.bookingnow.application.port.in.cliente;

import co.edu.udea.bookingnow.domain.model.Cliente;

public interface CrearClienteUseCase {
    Cliente crearCliente(RegistrarClienteCommand command);
}
