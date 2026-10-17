package co.edu.udea.bookingnow.application.port.out;

import co.edu.udea.bookingnow.domain.model.Reserva;
import co.edu.udea.bookingnow.domain.model.ReservaEnAgenda;
import java.time.LocalDate;
import java.util.List;

public interface ReservaRepositoryPort {
 void bloquearRecursos(Long idServicio,Long idEmpleado,Long idRecurso);
 List<ReservaEnAgenda> listarPorFecha(LocalDate fecha);
 Reserva guardar(Reserva reserva);
}
