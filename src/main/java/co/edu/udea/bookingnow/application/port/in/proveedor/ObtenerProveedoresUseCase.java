package co.edu.udea.bookingnow.application.port.in.proveedor;

import co.edu.udea.bookingnow.domain.model.Proveedor;
import java.util.List;

public interface ObtenerProveedoresUseCase {
    List<Proveedor> obtenerProveedores();
}
