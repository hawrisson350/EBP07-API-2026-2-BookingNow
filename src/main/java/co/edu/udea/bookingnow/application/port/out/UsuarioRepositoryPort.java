package co.edu.udea.bookingnow.application.port.out;

import co.edu.udea.bookingnow.domain.model.Usuario;
import java.util.Optional;

public interface UsuarioRepositoryPort {
    Optional<Usuario> obtenerPorCorreo(String correo);
    Optional<Usuario> obtenerPorId(Long id);
}
