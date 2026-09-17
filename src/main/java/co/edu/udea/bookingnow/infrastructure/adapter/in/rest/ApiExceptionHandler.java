package co.edu.udea.bookingnow.infrastructure.adapter.in.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(co.edu.udea.bookingnow.domain.exception.CredencialesInvalidasException.class)
    public ProblemDetail manejarCredencialesInvalidas() {
        return ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, "Usuario o contrasena incorrectos");
    }

    @ExceptionHandler(co.edu.udea.bookingnow.domain.exception.CuentaDuplicadaException.class)
    public ProblemDetail manejarCuentaDuplicada() {
        return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, "Ya existe una cuenta con ese nombre de usuario");
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ProblemDetail manejarDatosInvalidos(IllegalArgumentException exception) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, exception.getMessage());
    }
}
