package co.edu.udea.bookingnow.domain.model;

import java.time.DayOfWeek;
import java.time.LocalTime;

public record HorarioAtencion(Long idHorario, Long idNegocio, DayOfWeek dia, LocalTime horaApertura, LocalTime horaCierre) {}
