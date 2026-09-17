package co.edu.udea.bookingnow.domain.model;

import java.util.Set;

public record IdentidadAutenticada(Long id, String rol, String correo, String nombreUsuario, Set<String> roles) {}
