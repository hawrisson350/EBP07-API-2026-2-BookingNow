package co.edu.udea.bookingnow.infrastructure.adapter.in.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(co.edu.udea.bookingnow.domain.exception.CredencialesInvalidasException.class)
    public ProblemDetail manejarCredencialesInvalidas() {
        return ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, "Correo o contraseña incorrectos");
    }

    @ExceptionHandler(co.edu.udea.bookingnow.domain.exception.CuentaDuplicadaException.class)
    public ProblemDetail manejarCuentaDuplicada() {
        return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, "Ya existe una cuenta con ese nombre de usuario");
    }

    @ExceptionHandler(co.edu.udea.bookingnow.domain.exception.ConflictoException.class)
    public ProblemDetail conflicto(RuntimeException e) { return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, e.getMessage()); }
    @ExceptionHandler(co.edu.udea.bookingnow.domain.exception.NoEncontradoException.class)
    public ProblemDetail noEncontrado(RuntimeException e) { return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage()); }
    @ExceptionHandler({co.edu.udea.bookingnow.domain.exception.AccesoDenegadoException.class, co.edu.udea.bookingnow.domain.exception.CuentaNoHabilitadaException.class})
    public ProblemDetail denegado(RuntimeException e) { return ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, e.getMessage()); }
    @ExceptionHandler(co.edu.udea.bookingnow.domain.exception.ValidacionException.class)
    public ProblemDetail campos(co.edu.udea.bookingnow.domain.exception.ValidacionException e) {
        var problem = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
        problem.setProperty("campos", e.getCampos()); return problem;
    }
    @ExceptionHandler(org.springframework.dao.DataIntegrityViolationException.class)
    public ProblemDetail integridad() { return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT,
            "El registro entra en conflicto con datos existentes o tiene relaciones activas"); }

    @ExceptionHandler(IllegalArgumentException.class)
    public ProblemDetail manejarDatosInvalidos(IllegalArgumentException exception) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, exception.getMessage());
    }
}
