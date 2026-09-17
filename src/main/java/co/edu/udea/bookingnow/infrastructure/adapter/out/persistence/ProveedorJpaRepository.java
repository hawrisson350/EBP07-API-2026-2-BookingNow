package co.edu.udea.bookingnow.infrastructure.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProveedorJpaRepository extends JpaRepository<ProveedorJpaEntity, Long> {
    java.util.Optional<ProveedorJpaEntity> findByCorreo(String correo);
    boolean existsByNit(String nit);
    boolean existsByRazonSocialNormalizada(String razonSocialNormalizada);
    boolean existsByNombreUsuario(String nombreUsuario);
}
