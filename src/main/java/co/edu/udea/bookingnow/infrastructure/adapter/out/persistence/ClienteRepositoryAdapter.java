package co.edu.udea.bookingnow.infrastructure.adapter.out.persistence;

import co.edu.udea.bookingnow.application.port.out.ClienteRepositoryPort;
import co.edu.udea.bookingnow.domain.model.Cliente;
import co.edu.udea.bookingnow.domain.exception.CuentaDuplicadaException;
import org.springframework.stereotype.Component;
import org.springframework.dao.DataIntegrityViolationException;
import java.util.List;
import java.util.Optional;

@Component
public class ClienteRepositoryAdapter implements ClienteRepositoryPort {
    private final ClienteJpaRepository repository;

    public ClienteRepositoryAdapter(ClienteJpaRepository repository) { this.repository = repository; }

    @Override
    public Cliente guardar(Cliente cuenta) {
        try {
            return toDomain(repository.saveAndFlush(new ClienteJpaEntity(cuenta.getIdCliente(), cuenta.getCorreo(), cuenta.getNombreUsuario(), cuenta.getContrasenaHash())));
        } catch (DataIntegrityViolationException exception) {
            if (repository.existsByNombreUsuario(cuenta.getNombreUsuario())) {
                throw new CuentaDuplicadaException();
            }
            throw exception;
        }
    }

    @Override
    public List<Cliente> obtenerTodos() { return repository.findAll().stream().map(this::toDomain).toList(); }

    @Override
    public Optional<Cliente> obtenerPorId(Long id) { return repository.findById(id).map(this::toDomain); }

    @Override
    public boolean existeNombreUsuario(String nombreUsuario) { return repository.existsByNombreUsuario(nombreUsuario); }

    @Override
    public void eliminar(Long id) { repository.deleteById(id); }

    @Override
    public Optional<Cliente> obtenerPorNombreUsuario(String nombreUsuario) {
        return repository.findByNombreUsuario(nombreUsuario).map(this::toDomain);
    }

    private Cliente toDomain(ClienteJpaEntity entity) { return new Cliente(entity.getIdCliente(), entity.getCorreo(), entity.getNombreUsuario(), entity.getContrasenaHash()); }
}
