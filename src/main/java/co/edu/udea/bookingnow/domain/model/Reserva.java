package co.edu.udea.bookingnow.domain.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Instant;

public record Reserva(Long idReserva,Long idCliente,Long idServicio,Long idEmpleado,Long idRecurso,LocalDate fecha,LocalTime hora,String estado,Instant fechaCreacion) {}
