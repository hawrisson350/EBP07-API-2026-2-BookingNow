package co.edu.udea.bookingnow.application.port.in.reserva;

import co.edu.udea.bookingnow.domain.model.ReservaConsulta;
import java.util.List;

public interface ConsultarReservasActivasUseCase {
    List<ReservaConsulta> consultar(Long idCliente);
}
