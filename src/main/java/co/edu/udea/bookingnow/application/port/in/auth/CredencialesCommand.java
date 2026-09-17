package co.edu.udea.bookingnow.application.port.in.auth;

public record CredencialesCommand(String nombreUsuario, String contrasena) {
    @Override
    public String toString() { return "CredencialesCommand[datos omitidos]"; }
}
