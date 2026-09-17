package co.edu.udea.bookingnow.domain.exception;

public class CredencialesInvalidasException extends RuntimeException {
    public CredencialesInvalidasException() { super("Usuario o contrasena incorrectos"); }
}
