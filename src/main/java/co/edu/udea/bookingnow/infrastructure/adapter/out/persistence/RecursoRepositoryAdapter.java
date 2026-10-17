package co.edu.udea.bookingnow.infrastructure.adapter.out.persistence;

import co.edu.udea.bookingnow.application.port.out.RecursoRepositoryPort;
import co.edu.udea.bookingnow.domain.model.Recurso;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Repository @Transactional(readOnly=true)
public class RecursoRepositoryAdapter implements RecursoRepositoryPort {
 private final RecursoJpaRepository repo; private final EntityManager em;
 public RecursoRepositoryAdapter(RecursoJpaRepository repo,EntityManager em){this.repo=repo;this.em=em;}
 @Override @Transactional public Recurso guardar(Recurso r){var e=new RecursoJpaEntity(); e.setNegocio(em.getReference(NegocioJpaEntity.class,r.idNegocio())); e.setNombre(r.nombre()); e.setCantidad(r.cantidad()); e.setEstado(r.estado()); e.setDescripcion(r.descripcion()); return toDomain(repo.saveAndFlush(e));}
 @Override public List<Recurso> listarPorNegocio(Long id){return repo.findByNegocioIdNegocioOrderByIdRecursoAsc(id).stream().map(this::toDomain).toList();}
 @Override public java.util.Set<Long> idsDisponiblesPorNegocio(Long id,java.util.Set<Long> ids){if(ids.isEmpty())return java.util.Set.of();return repo.findByNegocioIdNegocioAndEstadoAndIdRecursoIn(id,"DISPONIBLE",ids).stream().map(RecursoJpaEntity::getIdRecurso).collect(java.util.stream.Collectors.toSet());}
 private Recurso toDomain(RecursoJpaEntity e){return new Recurso(e.getIdRecurso(),e.getNegocio().getIdNegocio(),e.getNombre(),e.getCantidad(),e.getEstado(),e.getDescripcion());}
}
