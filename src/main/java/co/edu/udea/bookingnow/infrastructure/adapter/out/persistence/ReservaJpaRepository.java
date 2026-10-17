package co.edu.udea.bookingnow.infrastructure.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface ReservaJpaRepository extends JpaRepository<ReservaJpaEntity,Long> {
 List<ReservaJpaEntity> findByFechaAndEstadoNotOrderByHoraAsc(LocalDate fecha,String estado);
}
