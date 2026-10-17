package co.edu.udea.bookingnow.application.port.in.negocio;

import java.time.DayOfWeek;
import java.time.LocalTime;

public record HorarioAtencionCommand(DayOfWeek dia,LocalTime horaApertura,LocalTime horaCierre) {}
