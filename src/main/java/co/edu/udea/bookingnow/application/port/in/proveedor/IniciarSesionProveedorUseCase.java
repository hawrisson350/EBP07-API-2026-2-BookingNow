package co.edu.udea.bookingnow.application.port.in.proveedor;
import co.edu.udea.bookingnow.application.port.in.auth.CredencialesCommand;

import co.edu.udea.bookingnow.domain.model.Proveedor;

public interface IniciarSesionProveedorUseCase {
    Proveedor iniciarSesionProveedor(CredencialesCommand command);
}
