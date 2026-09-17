package co.edu.udea.bookingnow.domain.exception;

public class CuentaNoHabilitadaException extends RuntimeException {
    public CuentaNoHabilitadaException() { super("La cuenta no se encuentra habilitada para iniciar sesión"); }
}
