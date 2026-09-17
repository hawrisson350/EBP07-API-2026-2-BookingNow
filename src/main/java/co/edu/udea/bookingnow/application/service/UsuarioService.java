package co.edu.udea.bookingnow.application.service;

import co.edu.udea.bookingnow.application.port.in.auth.CredencialesCommand;
import co.edu.udea.bookingnow.application.port.in.usuario.IniciarSesionUsuarioUseCase;
import co.edu.udea.bookingnow.application.port.out.ContrasenaPort;
import co.edu.udea.bookingnow.application.port.out.UsuarioRepositoryPort;
import co.edu.udea.bookingnow.domain.exception.CredencialesInvalidasException;
import co.edu.udea.bookingnow.domain.exception.CuentaNoHabilitadaException;
import co.edu.udea.bookingnow.domain.model.EstadoCuenta;
import co.edu.udea.bookingnow.domain.model.Usuario;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class UsuarioService implements IniciarSesionUsuarioUseCase {
    private static final Logger log = LoggerFactory.getLogger(UsuarioService.class);
    private final UsuarioRepositoryPort usuarios;
    private final ContrasenaPort contrasenas;
    public UsuarioService(UsuarioRepositoryPort usuarios, ContrasenaPort contrasenas) { this.usuarios = usuarios; this.contrasenas = contrasenas; }
    public Usuario iniciarSesionUsuario(CredencialesCommand command) {
        String correo = command.correo().strip().toLowerCase(java.util.Locale.ROOT);
        var encontrado = usuarios.obtenerPorCorreo(correo);
        if (encontrado.isEmpty()) {
            log.warn("admin_login result=usuario_no_encontrado");
            throw new CredencialesInvalidasException();
        }
        var usuario = encontrado.get();
        if (!contrasenas.coincide(command.contrasena(), usuario.getContrasenaHash())) {
            log.warn("admin_login result=contrasena_incorrecta id_usuario={}", usuario.getIdUsuario());
            throw new CredencialesInvalidasException();
        }
        if (usuario.getEstado() != EstadoCuenta.ACTIVA) {
            log.warn("admin_login result=cuenta_no_habilitada id_usuario={} estado={}", usuario.getIdUsuario(), usuario.getEstado());
            throw new CuentaNoHabilitadaException();
        }
        log.info("admin_login result=exitoso id_usuario={}", usuario.getIdUsuario());
        return usuario;
    }
}
