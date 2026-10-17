package co.edu.udea.bookingnow.domain.model;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservaEnAgenda(Long idReserva,Long idServicio,Long idEmpleado,Long idRecurso,LocalDate fecha,LocalTime hora,String estado,Integer duracionMinutos) {}
