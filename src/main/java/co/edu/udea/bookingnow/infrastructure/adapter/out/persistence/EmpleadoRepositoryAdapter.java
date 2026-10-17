package co.edu.udea.bookingnow.infrastructure.adapter.out.persistence;

import co.edu.udea.bookingnow.application.port.out.EmpleadoRepositoryPort;
import co.edu.udea.bookingnow.domain.model.Empleado;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Repository @Transactional(readOnly=true)
public class EmpleadoRepositoryAdapter implements EmpleadoRepositoryPort {
 private final EmpleadoJpaRepository repo; private final EntityManager em;
 public EmpleadoRepositoryAdapter(EmpleadoJpaRepository repo,EntityManager em){this.repo=repo;this.em=em;}
 @Override @Transactional public Empleado guardar(Empleado e){var entity=new EmpleadoJpaEntity(); entity.setNegocio(em.getReference(NegocioJpaEntity.class,e.idNegocio())); entity.setNombres(e.nombres()); entity.setDocumento(e.documento()); entity.setTelefono(e.telefono()); entity.setCorreo(e.correo()); return toDomain(repo.saveAndFlush(entity));}
 @Override public List<Empleado> listarPorNegocio(Long id){return repo.findByNegocioIdNegocioOrderByIdEmpleadoAsc(id).stream().map(this::toDomain).toList();}
 @Override public boolean existeDocumentoEnNegocio(Long id,String doc){return repo.existsByNegocioIdNegocioAndDocumento(id,doc);}
 @Override public java.util.Set<Long> idsPorNegocio(Long id,java.util.Set<Long> ids){if(ids.isEmpty())return java.util.Set.of();return repo.findByNegocioIdNegocioAndIdEmpleadoIn(id,ids).stream().map(EmpleadoJpaEntity::getIdEmpleado).collect(java.util.stream.Collectors.toSet());}
 private Empleado toDomain(EmpleadoJpaEntity e){return new Empleado(e.getIdEmpleado(),e.getNegocio().getIdNegocio(),e.getNombres(),e.getDocumento(),e.getTelefono(),e.getCorreo());}
}
