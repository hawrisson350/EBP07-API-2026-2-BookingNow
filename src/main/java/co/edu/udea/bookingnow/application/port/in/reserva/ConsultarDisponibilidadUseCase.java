package co.edu.udea.bookingnow.application.port.in.reserva;

import co.edu.udea.bookingnow.domain.model.OpcionDisponibilidad;
import java.time.LocalDate;
import java.util.List;

public interface ConsultarDisponibilidadUseCase { List<OpcionDisponibilidad> consultar(Long idServicio,LocalDate fecha); }
