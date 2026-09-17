package co.edu.udea.bookingnow.infrastructure.adapter.in.rest.dto;

import co.edu.udea.bookingnow.domain.model.Proveedor;

public record ProveedorResponse(Long idProveedor, String correo, String nombreUsuario, String razonSocial, String nit) {
    public static ProveedorResponse from(Proveedor cuenta) {
        return new ProveedorResponse(cuenta.getIdProveedor(), cuenta.getCorreo(), cuenta.getNombreUsuario(), cuenta.getRazonSocial(), cuenta.getNit());
    }
}
