package co.edu.udea.bookingnow.infrastructure.adapter.out.persistence;

import co.edu.udea.bookingnow.application.port.out.ClienteRepositoryPort;
import co.edu.udea.bookingnow.domain.model.Cliente;
import co.edu.udea.bookingnow.domain.exception.CuentaDuplicadaException;
import org.springframework.stereotype.Repository;
import org.springframework.dao.DataIntegrityViolationException;
import java.util.List;
import java.util.Optional;

@Repository
public class ClienteRepositoryAdapter implements ClienteRepositoryPort {
    private final ClienteJpaRepository repository;
    @jakarta.persistence.PersistenceContext private jakarta.persistence.EntityManager em;

    public ClienteRepositoryAdapter(ClienteJpaRepository repository) { this.repository = repository; }

    @Override
    @org.springframework.transaction.annotation.Transactional
    public Cliente guardar(Cliente cuenta) {
        if (cuenta.getIdCliente() == null) {
            em.persist(new CorreoRegistradoJpaEntity(cuenta.getCorreo(), "CLIENTE"));
            em.flush();
        }
        var entity = new ClienteJpaEntity(cuenta.getIdCliente(), cuenta.getCorreo(), cuenta.getNombreUsuario(), cuenta.getContrasenaHash());
        entity.setEstado(cuenta.getEstado());
        return toDomain(repository.saveAndFlush(entity));
    }

    @Override
    public List<Cliente> obtenerTodos() { return repository.findAll().stream().map(this::toDomain).toList(); }

    @Override
    public Optional<Cliente> obtenerPorId(Long id) { return repository.findById(id).map(this::toDomain); }

    @Override
    public boolean existeNombreUsuario(String nombreUsuario) { return repository.existsByNombreUsuario(nombreUsuario); }

    @Override
    @org.springframework.transaction.annotation.Transactional
    public void eliminar(Long id) {
        repository.findById(id).ifPresent(cuenta -> {
            repository.delete(cuenta);
            repository.flush();
            var registro = em.find(CorreoRegistradoJpaEntity.class, cuenta.getCorreo());
            if (registro != null) { em.remove(registro); }
        });
    }

    @Override
    public Optional<Cliente> obtenerPorCorreo(String correo) {
        return repository.findByCorreo(correo).map(this::toDomain);
    }

    private Cliente toDomain(ClienteJpaEntity entity) { var cuenta = new Cliente(entity.getIdCliente(), entity.getCorreo(), entity.getNombreUsuario(), entity.getContrasenaHash()); cuenta.setEstado(entity.getEstado()); return cuenta; }
}
