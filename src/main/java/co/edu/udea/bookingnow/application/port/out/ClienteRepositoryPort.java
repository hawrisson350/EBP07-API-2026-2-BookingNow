package co.edu.udea.bookingnow.application.port.out;

import co.edu.udea.bookingnow.domain.model.Cliente;
import java.util.List;
import java.util.Optional;

public interface ClienteRepositoryPort {
    Cliente guardar(Cliente cliente);
    List<Cliente> obtenerTodos();
    Optional<Cliente> obtenerPorId(Long id);
    Optional<Cliente> obtenerPorNombreUsuario(String nombreUsuario);
    boolean existeNombreUsuario(String nombreUsuario);
    void eliminar(Long id);
}
