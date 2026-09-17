package co.edu.udea.bookingnow.infrastructure.adapter.out.persistence;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioJpaRepository extends JpaRepository<UsuarioJpaEntity, Long> {
    Optional<UsuarioJpaEntity> findByCorreo(String correo);
}
