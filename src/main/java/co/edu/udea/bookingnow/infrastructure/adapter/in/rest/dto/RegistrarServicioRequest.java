package co.edu.udea.bookingnow.infrastructure.adapter.in.rest.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import co.edu.udea.bookingnow.application.port.in.servicio.RegistrarServicioCommand;

public record RegistrarServicioRequest(
        String nombre,
        Integer duracionMinutos,
        java.math.BigDecimal precio,
        String descripcion,
        @JsonAlias("imagenReferencia") String imagenReferenciaBase64) {
    public RegistrarServicioCommand toCommand() {
        return new RegistrarServicioCommand(nombre, duracionMinutos, precio, descripcion, imagenReferenciaBase64);
    }
}
