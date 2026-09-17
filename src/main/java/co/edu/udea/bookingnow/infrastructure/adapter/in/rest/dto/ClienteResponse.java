package co.edu.udea.bookingnow.infrastructure.adapter.in.rest.dto;

import co.edu.udea.bookingnow.domain.model.Cliente;

public record ClienteResponse(Long idCliente, String correo, String nombreUsuario) {
    public static ClienteResponse from(Cliente cuenta) {
        return new ClienteResponse(cuenta.getIdCliente(), cuenta.getCorreo(), cuenta.getNombreUsuario());
    }
}
