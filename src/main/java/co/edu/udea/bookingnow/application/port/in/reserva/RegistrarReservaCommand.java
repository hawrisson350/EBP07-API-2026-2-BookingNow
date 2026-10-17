package co.edu.udea.bookingnow.application.port.in.reserva;

import java.time.LocalDate;
import java.time.LocalTime;

public record RegistrarReservaCommand(Long idServicio,Long idEmpleado,Long idRecurso,LocalDate fecha,LocalTime hora) {}
