package co.edu.udea.bookingnow.application.port.in.servicio;

import co.edu.udea.bookingnow.domain.model.ServicioDetalle;
import java.util.Optional;

public interface ObtenerServicioDetalleUseCase { Optional<ServicioDetalle> obtener(Long idServicio); }
