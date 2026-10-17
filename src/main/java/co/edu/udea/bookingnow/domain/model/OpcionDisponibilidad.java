package co.edu.udea.bookingnow.domain.model;

import java.time.LocalTime;
import java.util.List;

public record OpcionDisponibilidad(LocalTime hora,List<Long> empleadosDisponibles,List<Long> recursosDisponibles) {}
