package co.edu.udea.bookingnow.application.port.in.proveedor;

import co.edu.udea.bookingnow.domain.model.Proveedor;

public interface ObtenerProveedorUseCase {
    java.util.Optional<Proveedor> obtenerProveedor(Long id);
}
