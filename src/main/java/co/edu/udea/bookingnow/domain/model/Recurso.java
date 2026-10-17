package co.edu.udea.bookingnow.domain.model;

public record Recurso(Long idRecurso, Long idNegocio, String nombre, Integer cantidad, String estado, String descripcion) {}
