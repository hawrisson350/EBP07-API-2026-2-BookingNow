package co.edu.udea.bookingnow.application.port.in.cliente;

import co.edu.udea.bookingnow.domain.model.Cliente;
import java.util.List;

public interface ObtenerClientesUseCase {
    List<Cliente> obtenerClientes();
}
