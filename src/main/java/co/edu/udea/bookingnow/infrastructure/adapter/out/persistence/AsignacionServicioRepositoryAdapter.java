package co.edu.udea.bookingnow.infrastructure.adapter.out.persistence;

import co.edu.udea.bookingnow.application.port.out.AsignacionServicioRepositoryPort;
import co.edu.udea.bookingnow.domain.model.AsignacionServicio;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

@Repository @Transactional(readOnly=true)
public class AsignacionServicioRepositoryAdapter implements AsignacionServicioRepositoryPort {
 private final EntityManager em;
 public AsignacionServicioRepositoryAdapter(EntityManager em){this.em=em;}
 @Override public Optional<AsignacionServicio> obtener(Long id){var s=em.find(ServicioJpaEntity.class,id);if(s==null)return Optional.empty();return Optional.of(toDomain(s));}
 @Override @Transactional public AsignacionServicio reemplazar(AsignacionServicio a){var s=em.find(ServicioJpaEntity.class,a.idServicio());if(s==null)throw new jakarta.persistence.EntityNotFoundException();s.setEmpleados(new HashSet<>());s.setRecursos(new HashSet<>());for(Long id:a.idEmpleados())s.getEmpleados().add(em.getReference(EmpleadoJpaEntity.class,id));for(Long id:a.idRecursos())s.getRecursos().add(em.getReference(RecursoJpaEntity.class,id));s.setRequiereEmpleados(a.requiereEmpleados());s.setRequiereRecursos(a.requiereRecursos());em.flush();return toDomain(s);}
 private AsignacionServicio toDomain(ServicioJpaEntity s){return new AsignacionServicio(s.getIdServicio(),s.isRequiereEmpleados(),s.isRequiereRecursos(),s.getEmpleados().stream().map(EmpleadoJpaEntity::getIdEmpleado).sorted().toList(),s.getRecursos().stream().map(RecursoJpaEntity::getIdRecurso).sorted().toList());}
}
