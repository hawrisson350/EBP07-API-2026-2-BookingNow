package co.edu.udea.bookingnow.domain.model;
public record Negocio(Long idNegocio, Long idProveedor, String nombre, String correo, String numContacto, String direccion, String categoria, boolean modalidadVirtual, String fotoPrincipalBase64, java.time.Instant fechaRegistro, java.util.List<Multimedia> galeria) {}
