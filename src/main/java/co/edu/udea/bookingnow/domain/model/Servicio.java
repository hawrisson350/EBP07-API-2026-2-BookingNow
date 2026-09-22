package co.edu.udea.bookingnow.domain.model;
public record Servicio(Long idServicio, Long idNegocio, String nombre, Integer duracionMinutos, java.math.BigDecimal precio, String descripcion, String imagenReferenciaBase64, String estado, java.time.Instant fechaRegistro) {}
