package co.edu.udea.bookingnow.application.port.in.servicio;

import java.util.List;

public record AsignarPersonalYRecursosCommand(boolean requiereEmpleados,boolean requiereRecursos,List<Long> idEmpleados,List<Long> idRecursos) {}
