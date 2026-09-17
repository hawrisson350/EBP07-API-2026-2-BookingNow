package co.edu.udea.bookingnow.infrastructure.adapter.out.persistence;

import co.edu.udea.bookingnow.application.port.out.ProveedorRepositoryPort;
import co.edu.udea.bookingnow.domain.model.Proveedor;
import co.edu.udea.bookingnow.domain.exception.CuentaDuplicadaException;
import org.springframework.stereotype.Repository;
import org.springframework.dao.DataIntegrityViolationException;
import java.util.List;
import java.util.Optional;

@Repository
public class ProveedorRepositoryAdapter implements ProveedorRepositoryPort {
    private final ProveedorJpaRepository repository;
    @jakarta.persistence.PersistenceContext private jakarta.persistence.EntityManager em;

    public ProveedorRepositoryAdapter(ProveedorJpaRepository repository) { this.repository = repository; }

    @Override
    @org.springframework.transaction.annotation.Transactional
    public Proveedor guardar(Proveedor cuenta) {
        if (cuenta.getIdProveedor() == null) {
            em.persist(new CorreoRegistradoJpaEntity(cuenta.getCorreo(), "PROVEEDOR"));
            em.flush();
        }
        var entity = new ProveedorJpaEntity(cuenta.getIdProveedor(), cuenta.getCorreo(), cuenta.getNombreUsuario(), cuenta.getRazonSocial(), cuenta.getNit(), cuenta.getContrasenaHash());
        entity.setEstado(cuenta.getEstado());
        return toDomain(repository.saveAndFlush(entity));
    }

    @Override
    public List<Proveedor> obtenerTodos() { return repository.findAll().stream().map(this::toDomain).toList(); }

    @Override
    public Optional<Proveedor> obtenerPorId(Long id) { return repository.findById(id).map(this::toDomain); }

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
    public Optional<Proveedor> obtenerPorCorreo(String correo) {
        return repository.findByCorreo(correo).map(this::toDomain);
    }

    public boolean existeNit(String nit) { return repository.existsByNit(nit); }
    public boolean existeRazonSocial(String razon) { return repository.existsByRazonSocialNormalizada(razon); }

    private Proveedor toDomain(ProveedorJpaEntity entity) { var cuenta = new Proveedor(entity.getIdProveedor(), entity.getCorreo(), entity.getNombreUsuario(), entity.getRazonSocial(), entity.getNit(), entity.getContrasenaHash()); cuenta.setEstado(entity.getEstado()); return cuenta; }
}
