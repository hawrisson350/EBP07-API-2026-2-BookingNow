package co.edu.udea.bookingnow.application.port.in.servicio;
public record RegistrarServicioCommand(String nombre, Integer duracionMinutos, java.math.BigDecimal precio, String descripcion, String imagenReferenciaBase64) {}
