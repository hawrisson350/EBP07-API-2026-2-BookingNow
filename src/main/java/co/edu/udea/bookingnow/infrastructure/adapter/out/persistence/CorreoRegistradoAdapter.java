package co.edu.udea.bookingnow.infrastructure.adapter.out.persistence;
import co.edu.udea.bookingnow.application.port.out.CorreoRegistradoPort;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Component;
import java.util.Optional;
@Component
public class CorreoRegistradoAdapter implements CorreoRegistradoPort {
    private final EntityManager em;
    public CorreoRegistradoAdapter(EntityManager em) { this.em = em; }
    public Optional<String> tipoPorCorreo(String correo) {
        return Optional.ofNullable(em.find(CorreoRegistradoJpaEntity.class, correo)).map(CorreoRegistradoJpaEntity::getTipo);
    }
}
