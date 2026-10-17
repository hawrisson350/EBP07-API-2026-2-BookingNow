package co.edu.udea.bookingnow.application.port.in.recurso;

public record RegistrarRecursoCommand(String nombre, Integer cantidad, String estado, String descripcion) {}
