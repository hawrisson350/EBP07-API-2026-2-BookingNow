package co.edu.udea.bookingnow.infrastructure.adapter.in.rest.dto;

import co.edu.udea.bookingnow.domain.model.Servicio;

public record ServicioResponse(Long idServicio, Long idNegocio, String nombre, Integer duracionMinutos,
        java.math.BigDecimal precio, String descripcion, String imagenReferenciaBase64,
        String imagenReferencia, String imagen_referencia_base64, String estado, java.time.Instant fechaRegistro) {
    public static ServicioResponse from(Servicio servicio) {
        return new ServicioResponse(servicio.idServicio(), servicio.idNegocio(), servicio.nombre(),
                servicio.duracionMinutos(), servicio.precio(), servicio.descripcion(),
                servicio.imagenReferenciaBase64(), servicio.imagenReferenciaBase64(), servicio.imagenReferenciaBase64(),
                servicio.estado(), servicio.fechaRegistro());
    }
}
