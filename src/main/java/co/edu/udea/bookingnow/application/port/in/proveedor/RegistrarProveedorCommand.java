package co.edu.udea.bookingnow.application.port.in.proveedor;

public record RegistrarProveedorCommand(String correo, String nombreUsuario, String razonSocial, String nit, String contrasena) {
    @Override
    public String toString() { return "RegistrarProveedorCommand[datos omitidos]"; }
}
