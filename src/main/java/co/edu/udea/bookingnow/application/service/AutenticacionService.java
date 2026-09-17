package co.edu.udea.bookingnow.application.service;
import co.edu.udea.bookingnow.application.port.in.auth.*;
import co.edu.udea.bookingnow.application.port.in.cliente.IniciarSesionClienteUseCase;
import co.edu.udea.bookingnow.application.port.in.proveedor.IniciarSesionProveedorUseCase;
import co.edu.udea.bookingnow.application.port.out.CorreoRegistradoPort;
import co.edu.udea.bookingnow.domain.model.IdentidadAutenticada;
import co.edu.udea.bookingnow.application.port.in.usuario.IniciarSesionUsuarioUseCase;
import java.util.Set;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Service
public class AutenticacionService implements IniciarSesionUseCase {
    private static final Logger log = LoggerFactory.getLogger(AutenticacionService.class);
    private final CorreoRegistradoPort correos;
    private final IniciarSesionUsuarioUseCase usuarios;
    private final IniciarSesionClienteUseCase clientes;
    private final IniciarSesionProveedorUseCase proveedores;
    public AutenticacionService(CorreoRegistradoPort correos, IniciarSesionUsuarioUseCase usuarios, IniciarSesionClienteUseCase clientes, IniciarSesionProveedorUseCase proveedores) {
        this.correos = correos; this.usuarios = usuarios; this.clientes = clientes; this.proveedores = proveedores;
    }
    public IdentidadAutenticada iniciarSesion(CredencialesCommand command) {
        ValidacionLogin.validar(command);
        String tipo = correos.tipoPorCorreo(command.correo().strip().toLowerCase(java.util.Locale.ROOT)).orElse("CLIENTE");
        log.info("login tipo_detectado={}", tipo);
        if (tipo.equals("PROVEEDOR")) {
            var cuenta = proveedores.iniciarSesionProveedor(command);
            return identidad(cuenta.getIdProveedor(), tipo, cuenta.getCorreo(), cuenta.getNombreUsuario());
        }
        if (tipo.equals("ADMINISTRADOR")) {
            var usuario = usuarios.iniciarSesionUsuario(command);
            return new IdentidadAutenticada(usuario.getIdUsuario(), usuario.getRol().name(), usuario.getCorreo(), usuario.getNombreUsuario(), Set.of(usuario.getRol().name()));
        }
        var cuenta = clientes.iniciarSesionCliente(command);
        return identidad(cuenta.getIdCliente(), "CLIENTE", cuenta.getCorreo(), cuenta.getNombreUsuario());
    }

    private IdentidadAutenticada identidad(Long id, String rolPrincipal, String correo, String nombreUsuario) {
        return new IdentidadAutenticada(id, rolPrincipal, correo, nombreUsuario, Set.of(rolPrincipal));
    }
}
