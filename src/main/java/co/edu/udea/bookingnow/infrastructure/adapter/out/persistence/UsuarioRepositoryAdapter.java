package co.edu.udea.bookingnow.infrastructure.adapter.out.persistence;

import co.edu.udea.bookingnow.application.port.out.UsuarioRepositoryPort;
import co.edu.udea.bookingnow.domain.model.Usuario;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class UsuarioRepositoryAdapter implements UsuarioRepositoryPort {
    private final UsuarioJpaRepository repository;
    public UsuarioRepositoryAdapter(UsuarioJpaRepository repository) { this.repository = repository; }
    public Optional<Usuario> obtenerPorCorreo(String correo) { return repository.findByCorreo(correo).map(this::aDominio); }
    public Optional<Usuario> obtenerPorId(Long id) { return repository.findById(id).map(this::aDominio); }
    private Usuario aDominio(UsuarioJpaEntity e) {
        return new Usuario(e.getIdUsuario(), e.getCorreo(), e.getNombreUsuario(), e.getContrasenaHash(), e.getRol(), e.getEstado());
    }
}
