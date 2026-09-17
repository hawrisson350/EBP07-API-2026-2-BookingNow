package co.edu.udea.bookingnow.infrastructure.adapter.out.persistence;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface NegocioJpaRepository extends JpaRepository<NegocioJpaEntity, Long> {
    Optional<NegocioJpaEntity> findByProveedorIdProveedor(Long idProveedor);
}
