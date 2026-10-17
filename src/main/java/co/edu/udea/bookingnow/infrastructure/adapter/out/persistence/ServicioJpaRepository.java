package co.edu.udea.bookingnow.infrastructure.adapter.out.persistence;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface ServicioJpaRepository extends JpaRepository<ServicioJpaEntity, Long> {
    List<ServicioJpaEntity> findByNegocioIdNegocioOrderByIdServicioAsc(Long idNegocio);
    org.springframework.data.domain.Page<ServicioJpaEntity> findAllByOrderByNombreAsc(org.springframework.data.domain.Pageable pageable);
    org.springframework.data.domain.Page<ServicioJpaEntity> findByNombreContainingIgnoreCaseOrderByNombreAsc(String nombre, org.springframework.data.domain.Pageable pageable);
    org.springframework.data.domain.Page<ServicioJpaEntity> findByTipoServicioContainingIgnoreCaseOrderByNombreAsc(String tipo, org.springframework.data.domain.Pageable pageable);
    org.springframework.data.domain.Page<ServicioJpaEntity> findByNombreContainingIgnoreCaseAndTipoServicioContainingIgnoreCaseOrderByNombreAsc(String nombre, String tipo, org.springframework.data.domain.Pageable pageable);
}
