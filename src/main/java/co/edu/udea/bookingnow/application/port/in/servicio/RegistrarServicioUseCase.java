package co.edu.udea.bookingnow.application.port.in.servicio;
import co.edu.udea.bookingnow.domain.model.*;
public interface RegistrarServicioUseCase { Servicio registrar(Long idProveedor, Long idNegocio, RegistrarServicioCommand command); }
