package co.edu.udea.bookingnow.infrastructure.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RecursoJpaRepository extends JpaRepository<RecursoJpaEntity,Long> {
 List<RecursoJpaEntity> findByNegocioIdNegocioOrderByIdRecursoAsc(Long idNegocio);
 java.util.List<RecursoJpaEntity> findByNegocioIdNegocioAndEstadoAndIdRecursoIn(Long idNegocio,String estado,java.util.Set<Long> ids);
}
