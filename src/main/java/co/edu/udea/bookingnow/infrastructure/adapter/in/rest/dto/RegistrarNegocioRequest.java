package co.edu.udea.bookingnow.infrastructure.adapter.in.rest.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import co.edu.udea.bookingnow.application.port.in.negocio.MultimediaCommand;
import co.edu.udea.bookingnow.application.port.in.negocio.RegistrarNegocioCommand;

public record RegistrarNegocioRequest(
        String nombre,
        String correo,
        String numContacto,
        String direccion,
        String categoria,
        Boolean modalidadVirtual,
        @JsonAlias("fotoPrincipal") String fotoPrincipalBase64,
        java.util.List<MultimediaCommand> galeria) {
    public RegistrarNegocioCommand toCommand() {
        return new RegistrarNegocioCommand(nombre, correo, numContacto, direccion, categoria, modalidadVirtual, fotoPrincipalBase64, galeria);
    }
}
