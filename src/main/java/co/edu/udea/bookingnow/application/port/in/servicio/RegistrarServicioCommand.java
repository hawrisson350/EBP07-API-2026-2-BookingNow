package co.edu.udea.bookingnow.application.port.in.servicio;
public record RegistrarServicioCommand(String nombre, Integer duracionMinutos, java.math.BigDecimal precio, String descripcion, String imagenReferenciaBase64, String tipoServicio, java.util.List<String> imagenesBase64) {
    public RegistrarServicioCommand(String nombre, Integer duracionMinutos, java.math.BigDecimal precio, String descripcion, String imagenReferenciaBase64, String tipoServicio) {
        this(nombre,duracionMinutos,precio,descripcion,imagenReferenciaBase64,tipoServicio,null);
    }
    public RegistrarServicioCommand(String nombre, Integer duracionMinutos, java.math.BigDecimal precio, String descripcion, String imagenReferenciaBase64) {
        this(nombre, duracionMinutos, precio, descripcion, imagenReferenciaBase64, null, null);
    }
}
