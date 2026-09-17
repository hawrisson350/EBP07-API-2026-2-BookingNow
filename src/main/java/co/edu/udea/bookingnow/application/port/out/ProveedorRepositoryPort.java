package co.edu.udea.bookingnow.application.port.out;

import co.edu.udea.bookingnow.domain.model.Proveedor;
import java.util.List;
import java.util.Optional;

public interface ProveedorRepositoryPort {
    Proveedor guardar(Proveedor proveedor);
    List<Proveedor> obtenerTodos();
    Optional<Proveedor> obtenerPorId(Long id);
    Optional<Proveedor> obtenerPorNombreUsuario(String nombreUsuario);
    boolean existeNombreUsuario(String nombreUsuario);
    void eliminar(Long id);
}
