package co.edu.udea.bookingnow.infrastructure.adapter.out.security;

import co.edu.udea.bookingnow.application.port.out.ContrasenaPort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class ContrasenaAdapter implements ContrasenaPort {
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
    public String codificar(String contrasena) {
        return encoder.encode(contrasena);
    }

    @Override
    public boolean coincide(String contrasena, String hash) {
        return encoder.matches(contrasena, hash);
    }
}
