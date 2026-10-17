package co.edu.udea.bookingnow.infrastructure.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface HorarioAtencionJpaRepository extends JpaRepository<HorarioAtencionJpaEntity,Long> {
 List<HorarioAtencionJpaEntity> findByNegocioIdNegocioOrderByDiaAsc(Long idNegocio);
 void deleteByNegocioIdNegocio(Long idNegocio);
}
