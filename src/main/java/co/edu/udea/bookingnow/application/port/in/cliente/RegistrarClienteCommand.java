package co.edu.udea.bookingnow.application.port.in.cliente;

public record RegistrarClienteCommand(String correo, String nombreUsuario, String contrasena) {
    @Override
    public String toString() { return "RegistrarClienteCommand[datos omitidos]"; }
}
