package co.edu.udea.bookingnow.application.port.out;
import java.util.Optional;
public interface CorreoRegistradoPort {
    Optional<String> tipoPorCorreo(String correo);
}
