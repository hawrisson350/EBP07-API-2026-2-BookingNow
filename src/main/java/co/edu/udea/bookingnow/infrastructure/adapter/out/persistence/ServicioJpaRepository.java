package co.edu.udea.bookingnow.infrastructure.adapter.out.persistence;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface ServicioJpaRepository extends JpaRepository<ServicioJpaEntity, Long> {
    List<ServicioJpaEntity> findByNegocioIdNegocioOrderByIdServicioAsc(Long idNegocio);
}
