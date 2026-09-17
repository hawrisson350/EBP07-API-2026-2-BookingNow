package co.edu.udea.bookingnow.application.port.in.proveedor;

import co.edu.udea.bookingnow.domain.model.Proveedor;

public interface CrearProveedorUseCase {
    Proveedor crearProveedor(RegistrarProveedorCommand command);
}
