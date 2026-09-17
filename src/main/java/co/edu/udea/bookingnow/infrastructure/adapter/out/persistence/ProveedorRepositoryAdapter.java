package co.edu.udea.bookingnow.infrastructure.adapter.out.persistence;

import co.edu.udea.bookingnow.application.port.out.ProveedorRepositoryPort;
import co.edu.udea.bookingnow.domain.model.Proveedor;
import co.edu.udea.bookingnow.domain.exception.CuentaDuplicadaException;
import org.springframework.stereotype.Component;
import org.springframework.dao.DataIntegrityViolationException;
import java.util.List;
import java.util.Optional;

@Component
public class ProveedorRepositoryAdapter implements ProveedorRepositoryPort {
    private final ProveedorJpaRepository repository;

    public ProveedorRepositoryAdapter(ProveedorJpaRepository repository) { this.repository = repository; }

    @Override
    public Proveedor guardar(Proveedor cuenta) {
        try {
            return toDomain(repository.saveAndFlush(new ProveedorJpaEntity(cuenta.getIdProveedor(), cuenta.getCorreo(), cuenta.getNombreUsuario(), cuenta.getRazonSocial(), cuenta.getNit(), cuenta.getContrasenaHash())));
        } catch (DataIntegrityViolationException exception) {
            if (repository.existsByNombreUsuario(cuenta.getNombreUsuario())) {
                throw new CuentaDuplicadaException();
            }
            throw exception;
        }
    }

    @Override
    public List<Proveedor> obtenerTodos() { return repository.findAll().stream().map(this::toDomain).toList(); }

    @Override
    public Optional<Proveedor> obtenerPorId(Long id) { return repository.findById(id).map(this::toDomain); }

    @Override
    public boolean existeNombreUsuario(String nombreUsuario) { return repository.existsByNombreUsuario(nombreUsuario); }

    @Override
    public void eliminar(Long id) { repository.deleteById(id); }

    @Override
    public Optional<Proveedor> obtenerPorNombreUsuario(String nombreUsuario) {
        return repository.findByNombreUsuario(nombreUsuario).map(this::toDomain);
    }

    private Proveedor toDomain(ProveedorJpaEntity entity) { return new Proveedor(entity.getIdProveedor(), entity.getCorreo(), entity.getNombreUsuario(), entity.getRazonSocial(), entity.getNit(), entity.getContrasenaHash()); }
}
