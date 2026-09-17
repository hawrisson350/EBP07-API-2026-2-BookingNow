package co.edu.udea.bookingnow.application.service;
import co.edu.udea.bookingnow.application.port.in.auth.CredencialesCommand;
import co.edu.udea.bookingnow.domain.exception.ValidacionException;
import java.util.LinkedHashMap;
final class ValidacionLogin {
    static void validar(CredencialesCommand command) {
        var errors = new LinkedHashMap<String, String>();
        if (command == null || command.correo() == null || command.correo().isBlank()) { errors.put("correo", "El correo es obligatorio"); }
        else { try { ValidacionCuenta.correo(command.correo()); } catch (IllegalArgumentException e) { errors.put("correo", "Ingresa un correo electrónico válido"); } }
        if (command == null || command.contrasena() == null || command.contrasena().isBlank()) { errors.put("contrasena", "La contraseña es obligatoria"); }
        else if (command.contrasena().getBytes(java.nio.charset.StandardCharsets.UTF_8).length > 72) { errors.put("contrasena", "La contraseña admite hasta 72 bytes UTF-8"); }
        if (!errors.isEmpty()) { throw new ValidacionException(errors); }
    }
}
