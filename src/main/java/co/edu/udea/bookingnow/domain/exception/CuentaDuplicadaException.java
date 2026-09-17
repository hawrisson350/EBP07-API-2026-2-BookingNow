package co.edu.udea.bookingnow.domain.exception;

public class CuentaDuplicadaException extends RuntimeException {
    public CuentaDuplicadaException() {
        super("Ya existe una cuenta con ese nombre de usuario");
    }
}
