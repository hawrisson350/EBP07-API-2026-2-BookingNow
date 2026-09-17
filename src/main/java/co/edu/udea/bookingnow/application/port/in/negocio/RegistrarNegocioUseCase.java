package co.edu.udea.bookingnow.application.port.in.negocio;
import co.edu.udea.bookingnow.domain.model.*;
public interface RegistrarNegocioUseCase { Negocio registrar(Long idProveedor, RegistrarNegocioCommand command); }
