package co.edu.udea.bookingnow.application.port.in.cliente;

import co.edu.udea.bookingnow.domain.model.Cliente;

public interface ObtenerClienteUseCase {
    java.util.Optional<Cliente> obtenerCliente(Long id);
}
