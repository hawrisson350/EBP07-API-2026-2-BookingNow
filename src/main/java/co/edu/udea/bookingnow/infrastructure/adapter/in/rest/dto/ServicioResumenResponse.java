package co.edu.udea.bookingnow.infrastructure.adapter.in.rest.dto;

import co.edu.udea.bookingnow.domain.model.ServicioResumen;

public record ServicioResumenResponse(
        Long idServicio,
        Long idNegocio,
        String nombre,
        String tipoServicio,
        Integer duracionMinutos,
        java.math.BigDecimal precio,
        String descripcion,
        String estado) {

    public static ServicioResumenResponse from(ServicioResumen servicio) {
        return new ServicioResumenResponse(
                servicio.idServicio(), servicio.idNegocio(), servicio.nombre(), servicio.tipoServicio(),
                servicio.duracionMinutos(), servicio.precio(), servicio.descripcion(), servicio.estado());
    }
}
