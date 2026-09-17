package co.edu.udea.bookingnow.application.port.out;

public interface ContrasenaPort {
    String codificar(String contrasena);
    boolean coincide(String contrasena, String hash);
}
