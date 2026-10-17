package co.edu.udea.bookingnow.infrastructure.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EmpleadoJpaRepository extends JpaRepository<EmpleadoJpaEntity,Long> {
 List<EmpleadoJpaEntity> findByNegocioIdNegocioOrderByIdEmpleadoAsc(Long idNegocio);
 boolean existsByNegocioIdNegocioAndDocumento(Long idNegocio,String documento);
 java.util.List<EmpleadoJpaEntity> findByNegocioIdNegocioAndIdEmpleadoIn(Long idNegocio,java.util.Set<Long> ids);
}
