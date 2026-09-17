package co.edu.udea.bookingnow.infrastructure.adapter.out.persistence;
import co.edu.udea.bookingnow.application.port.out.NegocioRepositoryPort;
import co.edu.udea.bookingnow.domain.model.*;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
@Component
@Transactional(readOnly = true)
public class NegocioRepositoryAdapter implements NegocioRepositoryPort {
    private final NegocioJpaRepository repository;
    private final EntityManager em;
    public NegocioRepositoryAdapter(NegocioJpaRepository repository, EntityManager em) { this.repository = repository; this.em = em; }
    @Transactional
    public Negocio guardar(Negocio dato) {
        var entity = new NegocioJpaEntity();
        entity.setIdNegocio(dato.idNegocio());
        entity.setProveedor(em.getReference(ProveedorJpaEntity.class, dato.idProveedor()));
        entity.setNombre(dato.nombre());
        entity.setCorreo(dato.correo());
        entity.setNumContacto(dato.numContacto());
        entity.setDireccion(dato.direccion());
        entity.setCategoria(dato.categoria());
        entity.setModalidadVirtual(dato.modalidadVirtual());
        entity.setFotoPrincipal(dato.fotoPrincipal());
        entity.setFechaRegistro(dato.fechaRegistro());
        entity.setGaleria(dato.galeria().stream().map(m -> new MultimediaJpaValue(m.url(), m.tipo(), m.orden())).toList());
        return toDomain(repository.saveAndFlush(entity));
    }
    public List<Negocio> obtenerTodos() { return repository.findAll().stream().map(this::toDomain).toList(); }
    public Optional<Negocio> obtenerPorProveedor(Long id) { return repository.findByProveedorIdProveedor(id).map(this::toDomain); }
    public Optional<Negocio> obtenerPorId(Long id) { return repository.findById(id).map(this::toDomain); }
    private Negocio toDomain(NegocioJpaEntity e) { return new Negocio(e.getIdNegocio(), e.getProveedor() .getIdProveedor(), e.getNombre(), e.getCorreo(), e.getNumContacto(), e.getDireccion(), e.getCategoria(), e.getModalidadVirtual(), e.getFotoPrincipal(), e.getFechaRegistro(), e.getGaleria().stream().map(m -> new Multimedia(m.getUrl(), m.getTipo(), m.getOrden())).toList()); }
}
