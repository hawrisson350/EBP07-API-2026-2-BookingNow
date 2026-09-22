package co.edu.udea.bookingnow.infrastructure.adapter.out.persistence;
import co.edu.udea.bookingnow.application.port.out.ServicioRepositoryPort;
import co.edu.udea.bookingnow.domain.model.*;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
@Component
@Transactional(readOnly = true)
public class ServicioRepositoryAdapter implements ServicioRepositoryPort {
    private final ServicioJpaRepository repository;
    private final EntityManager em;
    public ServicioRepositoryAdapter(ServicioJpaRepository repository, EntityManager em) { this.repository = repository; this.em = em; }
    @Transactional
    public Servicio guardar(Servicio dato) {
        var entity = new ServicioJpaEntity();
        entity.setIdServicio(dato.idServicio());
        entity.setNegocio(em.getReference(NegocioJpaEntity.class, dato.idNegocio()));
        entity.setNombre(dato.nombre());
        entity.setDuracionMinutos(dato.duracionMinutos());
        entity.setPrecio(dato.precio());
        entity.setDescripcion(dato.descripcion());
        entity.setImagenReferenciaBase64(dato.imagenReferenciaBase64());
        entity.setEstado(dato.estado());
        entity.setFechaRegistro(dato.fechaRegistro());
        return toDomain(repository.saveAndFlush(entity));
    }
    public List<Servicio> listarPorNegocio(Long id) { return repository.findByNegocioIdNegocioOrderByIdServicioAsc(id).stream().map(this::toDomain).toList(); }
    private Servicio toDomain(ServicioJpaEntity e) { return new Servicio(e.getIdServicio(), e.getNegocio() .getIdNegocio(), e.getNombre(), e.getDuracionMinutos(), e.getPrecio(), e.getDescripcion(), e.getImagenReferenciaBase64(), e.getEstado(), e.getFechaRegistro()); }
}
