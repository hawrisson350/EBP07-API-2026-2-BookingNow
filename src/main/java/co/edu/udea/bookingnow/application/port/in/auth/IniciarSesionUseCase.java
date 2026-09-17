package co.edu.udea.bookingnow.application.port.in.auth;
import co.edu.udea.bookingnow.domain.model.IdentidadAutenticada;
public interface IniciarSesionUseCase { IdentidadAutenticada iniciarSesion(CredencialesCommand command); }
