package co.edu.udea.bookingnow.application.port.out;

import co.edu.udea.bookingnow.domain.model.Proveedor;
import java.util.List;
import java.util.Optional;

public interface ProveedorRepositoryPort {
    Proveedor guardar(Proveedor proveedor);
    List<Proveedor> obtenerTodos();
    Optional<Proveedor> obtenerPorId(Long id);
    Optional<Proveedor> obtenerPorCorreo(String correo);
    boolean existeNombreUsuario(String nombreUsuario);
    boolean existeNit(String nit);
    boolean existeRazonSocial(String razonSocialNormalizada);
    void eliminar(Long id);
}
