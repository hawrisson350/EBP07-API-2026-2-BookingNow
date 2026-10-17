package co.edu.udea.bookingnow.domain.model;

import java.math.BigDecimal;

public record ServicioResumen(
        Long idServicio,
        Long idNegocio,
        String nombre,
        String tipoServicio,
        Integer duracionMinutos,
        BigDecimal precio,
        String descripcion,
        String estado) {
}
