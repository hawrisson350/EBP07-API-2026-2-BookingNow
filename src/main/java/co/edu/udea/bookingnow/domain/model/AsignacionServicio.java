package co.edu.udea.bookingnow.domain.model;

import java.util.List;

public record AsignacionServicio(Long idServicio,boolean requiereEmpleados,boolean requiereRecursos,List<Long> idEmpleados,List<Long> idRecursos) {}
