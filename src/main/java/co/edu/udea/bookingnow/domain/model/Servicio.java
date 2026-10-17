package co.edu.udea.bookingnow.domain.model;
public record Servicio(Long idServicio, Long idNegocio, String nombre, Integer duracionMinutos, java.math.BigDecimal precio, String descripcion, String imagenReferenciaBase64, String estado, java.time.Instant fechaRegistro, String tipoServicio, java.util.List<String> imagenesBase64, boolean requiereEmpleados, boolean requiereRecursos, java.util.List<Long> idEmpleadosAsignados, java.util.List<Long> idRecursosAsignados) {
    public Servicio {
        imagenesBase64 = imagenesBase64 == null ?
                (imagenReferenciaBase64 == null ? java.util.List.of() : java.util.List.of(imagenReferenciaBase64)) :
                java.util.List.copyOf(imagenesBase64);
        idEmpleadosAsignados = idEmpleadosAsignados == null ? java.util.List.of() : java.util.List.copyOf(idEmpleadosAsignados);
        idRecursosAsignados = idRecursosAsignados == null ? java.util.List.of() : java.util.List.copyOf(idRecursosAsignados);
    }
    public Servicio(Long idServicio, Long idNegocio, String nombre, Integer duracionMinutos,
            java.math.BigDecimal precio, String descripcion, String imagenReferenciaBase64,
            String estado, java.time.Instant fechaRegistro, String tipoServicio) {
        this(idServicio,idNegocio,nombre,duracionMinutos,precio,descripcion,imagenReferenciaBase64,estado,fechaRegistro,tipoServicio,null,false,false,null,null);
    }
    public Servicio(Long idServicio, Long idNegocio, String nombre, Integer duracionMinutos,
            java.math.BigDecimal precio, String descripcion, String imagenReferenciaBase64,
            String estado, java.time.Instant fechaRegistro, String tipoServicio, java.util.List<String> imagenesBase64) {
        this(idServicio,idNegocio,nombre,duracionMinutos,precio,descripcion,imagenReferenciaBase64,estado,fechaRegistro,tipoServicio,imagenesBase64,false,false,null,null);
    }
    public Servicio(Long idServicio, Long idNegocio, String nombre, Integer duracionMinutos,
            java.math.BigDecimal precio, String descripcion, String imagenReferenciaBase64,
            String estado, java.time.Instant fechaRegistro) {
        this(idServicio, idNegocio, nombre, duracionMinutos, precio, descripcion,
                imagenReferenciaBase64, estado, fechaRegistro, null, null, false, false, null, null);
    }
}
