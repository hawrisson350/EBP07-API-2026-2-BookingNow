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
        entity.setTipoServicio(dato.tipoServicio());
        entity.setImagenesBase64(dato.imagenesBase64());
        entity.setDuracionMinutos(dato.duracionMinutos());
        entity.setPrecio(dato.precio());
        entity.setDescripcion(dato.descripcion());
        entity.setImagenReferenciaBase64(dato.imagenReferenciaBase64());
        entity.setEstado(dato.estado());
        entity.setFechaRegistro(dato.fechaRegistro());
        return toDomain(repository.saveAndFlush(entity));
    }
    public List<Servicio> listarPorNegocio(Long id) { return repository.findByNegocioIdNegocioOrderByIdServicioAsc(id).stream().map(this::toDomain).toList(); }
    public Optional<Servicio> obtenerPorId(Long id) { return repository.findById(id).map(this::toDomain); }
    public co.edu.udea.bookingnow.application.port.in.servicio.PaginaServicios buscar(String nombre, String tipo, int pagina, int tamano) {
        var pageable = org.springframework.data.domain.PageRequest.of(pagina, tamano);
        var resultado = nombre != null && tipo != null ? repository.findByNombreContainingIgnoreCaseAndTipoServicioContainingIgnoreCaseOrderByNombreAsc(nombre, tipo, pageable)
                : nombre != null ? repository.findByNombreContainingIgnoreCaseOrderByNombreAsc(nombre, pageable)
                : tipo != null ? repository.findByTipoServicioContainingIgnoreCaseOrderByNombreAsc(tipo, pageable)
                : repository.findAllByOrderByNombreAsc(pageable);
        return new co.edu.udea.bookingnow.application.port.in.servicio.PaginaServicios(resultado.getContent().stream().map(this::toResumen).toList(), resultado.getNumber(), resultado.getSize(), resultado.getTotalElements(), resultado.getTotalPages());
    }
    private ServicioResumen toResumen(ServicioJpaEntity e) { return new ServicioResumen(e.getIdServicio(), e.getNegocio().getIdNegocio(), e.getNombre(), e.getTipoServicio(), e.getDuracionMinutos(), e.getPrecio(), e.getDescripcion(), e.getEstado()); }
    private Servicio toDomain(ServicioJpaEntity e) { return new Servicio(e.getIdServicio(), e.getNegocio().getIdNegocio(), e.getNombre(), e.getDuracionMinutos(), e.getPrecio(), e.getDescripcion(), e.getImagenReferenciaBase64(), e.getEstado(), e.getFechaRegistro(), e.getTipoServicio(), e.getImagenesBase64(), e.isRequiereEmpleados(), e.isRequiereRecursos(), e.getEmpleados().stream().map(EmpleadoJpaEntity::getIdEmpleado).sorted().toList(), e.getRecursos().stream().map(RecursoJpaEntity::getIdRecurso).sorted().toList()); }
}
