package co.edu.udea.bookingnow.application.port.in.reserva;

import co.edu.udea.bookingnow.domain.model.ReservaConsulta;
import java.util.List;

public interface ConsultarHistorialReservasUseCase {
    List<ReservaConsulta> consultarHistorial(Long idCliente);
}
