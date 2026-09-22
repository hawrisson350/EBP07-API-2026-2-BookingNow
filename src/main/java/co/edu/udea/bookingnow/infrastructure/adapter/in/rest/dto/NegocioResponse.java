package co.edu.udea.bookingnow.infrastructure.adapter.in.rest.dto;

import co.edu.udea.bookingnow.domain.model.Negocio;
import co.edu.udea.bookingnow.domain.model.Multimedia;

public record NegocioResponse(Long idNegocio, Long idProveedor, String nombre, String correo, String numContacto,
        String direccion, String categoria, boolean modalidadVirtual, String fotoPrincipalBase64,
        String fotoPrincipal, java.time.Instant fechaRegistro, java.util.List<Multimedia> galeria) {
    public static NegocioResponse from(Negocio negocio) {
        return new NegocioResponse(negocio.idNegocio(), negocio.idProveedor(), negocio.nombre(), negocio.correo(),
                negocio.numContacto(), negocio.direccion(), negocio.categoria(), negocio.modalidadVirtual(),
                negocio.fotoPrincipalBase64(), negocio.fotoPrincipalBase64(), negocio.fechaRegistro(), negocio.galeria());
    }
}
