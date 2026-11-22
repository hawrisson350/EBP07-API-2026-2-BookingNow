package co.edu.udea.bookingnow.application.port.out;

import co.edu.udea.bookingnow.domain.model.Reserva;
import co.edu.udea.bookingnow.domain.model.ReservaEnAgenda;
import co.edu.udea.bookingnow.domain.model.ReservaConsulta;
import co.edu.udea.bookingnow.domain.model.ReservaCancelacion;
import co.edu.udea.bookingnow.domain.model.ReservaRecordatorio;
import java.time.LocalDate;
import java.util.List;

public interface ReservaRepositoryPort {
 void bloquearRecursos(Long idServicio,Long idEmpleado,Long idRecurso);
 List<ReservaEnAgenda> listarPorFecha(LocalDate fecha);
 List<ReservaConsulta> listarActivasPorCliente(Long idCliente, LocalDate desde, java.time.LocalTime desdeHora);
 List<ReservaConsulta> listarHistorialPorCliente(Long idCliente);
 List<ReservaRecordatorio> buscarRecordatoriosPendientes(java.time.LocalDateTime hasta);
 boolean marcarRecordatorioProcesado(Long idReserva, java.time.Instant enviadoEn);
 java.util.Optional<ReservaCancelacion> obtenerParaCancelar(Long idReserva);
 Reserva actualizarEstado(Long idReserva, String estado);
 Reserva guardar(Reserva reserva);
}
