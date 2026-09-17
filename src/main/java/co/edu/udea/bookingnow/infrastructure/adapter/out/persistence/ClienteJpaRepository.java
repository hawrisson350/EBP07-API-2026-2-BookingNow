package co.edu.udea.bookingnow.infrastructure.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteJpaRepository extends JpaRepository<ClienteJpaEntity, Long> {
    java.util.Optional<ClienteJpaEntity> findByCorreo(String correo);
    boolean existsByNombreUsuario(String nombreUsuario);
}
