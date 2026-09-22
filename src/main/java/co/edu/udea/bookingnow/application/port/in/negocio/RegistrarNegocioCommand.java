package co.edu.udea.bookingnow.application.port.in.negocio;
public record RegistrarNegocioCommand(String nombre, String correo, String numContacto, String direccion, String categoria, Boolean modalidadVirtual, String fotoPrincipalBase64, java.util.List<MultimediaCommand> galeria) {}
