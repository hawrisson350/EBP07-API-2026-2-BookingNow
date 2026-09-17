package co.edu.udea.bookingnow.application.port.in.usuario;

import co.edu.udea.bookingnow.application.port.in.auth.CredencialesCommand;
import co.edu.udea.bookingnow.domain.model.Usuario;

public interface IniciarSesionUsuarioUseCase {
    Usuario iniciarSesionUsuario(CredencialesCommand command);
}
