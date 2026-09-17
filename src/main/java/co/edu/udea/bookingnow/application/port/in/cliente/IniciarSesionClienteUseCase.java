package co.edu.udea.bookingnow.application.port.in.cliente;
import co.edu.udea.bookingnow.application.port.in.auth.CredencialesCommand;

import co.edu.udea.bookingnow.domain.model.Cliente;

public interface IniciarSesionClienteUseCase {
    Cliente iniciarSesionCliente(CredencialesCommand command);
}
