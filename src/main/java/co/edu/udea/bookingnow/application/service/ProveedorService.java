package co.edu.udea.bookingnow.application.service;
import co.edu.udea.bookingnow.application.port.in.proveedor.RegistrarProveedorCommand;
import co.edu.udea.bookingnow.application.port.in.proveedor.ObtenerProveedorUseCase;
import co.edu.udea.bookingnow.application.port.in.proveedor.ObtenerProveedoresUseCase;
import co.edu.udea.bookingnow.application.port.in.proveedor.IniciarSesionProveedorUseCase;
import co.edu.udea.bookingnow.application.port.in.proveedor.EliminarProveedorUseCase;
import co.edu.udea.bookingnow.application.port.in.auth.CredencialesCommand;
import co.edu.udea.bookingnow.application.port.in.proveedor.CrearProveedorUseCase;

import co.edu.udea.bookingnow.application.port.out.ProveedorRepositoryPort;
import co.edu.udea.bookingnow.application.port.out.ContrasenaPort;
import co.edu.udea.bookingnow.domain.model.Proveedor;
import co.edu.udea.bookingnow.domain.exception.CuentaDuplicadaException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProveedorService implements CrearProveedorUseCase, ObtenerProveedorUseCase,
        ObtenerProveedoresUseCase, EliminarProveedorUseCase, IniciarSesionProveedorUseCase {
    private final ProveedorRepositoryPort repository;
    private final ContrasenaPort contrasenaPort;
    private final String hashInexistente;
    private final co.edu.udea.bookingnow.application.port.out.CorreoRegistradoPort correos;

    public ProveedorService(ProveedorRepositoryPort repository, ContrasenaPort contrasenaPort, co.edu.udea.bookingnow.application.port.out.CorreoRegistradoPort correos) {
        this.correos = correos;
        this.repository = repository;
        this.contrasenaPort = contrasenaPort;
        this.hashInexistente = contrasenaPort.codificar(java.util.UUID.randomUUID().toString());
    }

    @Override
    public Proveedor crearProveedor(RegistrarProveedorCommand command) {
        if (command == null) { throw new IllegalArgumentException("Los datos son obligatorios"); }
        String correo = ValidacionCuenta.correo(command.correo());
        String nombreUsuario = ValidacionCuenta.nombreUsuario(command.nombreUsuario());
        ValidacionCuenta.contrasena(command.contrasena());
        String razonSocial = ValidacionCuenta.obligatorio(command.razonSocial(), "razonSocial");
        String nit = ValidacionCuenta.obligatorio(command.nit(), "nit");
        if (razonSocial.length() < 3) { throw new IllegalArgumentException("La razón social debe tener mínimo 3 caracteres"); }
        if (!nit.matches("[0-9]{9}-[0-9]")) { throw new IllegalArgumentException("El NIT debe tener formato #########-#"); }
        if (repository.existeNit(nit)) { throw new co.edu.udea.bookingnow.domain.exception.ConflictoException("El NIT ya está registrado"); }
        if (repository.existeRazonSocial(razonSocial.replaceAll("\\s+", " ").toLowerCase(java.util.Locale.ROOT))) {
            throw new co.edu.udea.bookingnow.domain.exception.ConflictoException("La razón social ya está registrada");
        }
        if (correos.tipoPorCorreo(correo).isPresent()) { throw new co.edu.udea.bookingnow.domain.exception.ConflictoException("El correo ya está en uso"); }
        if (repository.existeNombreUsuario(nombreUsuario)) { throw new CuentaDuplicadaException(); }
        return repository.guardar(new Proveedor(null, correo, nombreUsuario, razonSocial, nit, contrasenaPort.codificar(command.contrasena())));
    }

    @Override
    public List<Proveedor> obtenerProveedores() { return repository.obtenerTodos(); }
    @Override
    public Optional<Proveedor> obtenerProveedor(Long id) { return repository.obtenerPorId(id); }
    @Override
    public void eliminarProveedor(Long id) { repository.eliminar(id); }
    @Override
    public Proveedor iniciarSesionProveedor(CredencialesCommand command) {
        ValidacionLogin.validar(command);
        var cuenta = repository.obtenerPorCorreo(command.correo().strip().toLowerCase(java.util.Locale.ROOT));
        boolean valida = contrasenaPort.coincide(command.contrasena(),
                cuenta.map(Proveedor::getContrasenaHash).orElse(hashInexistente));
        if (!valida || cuenta.isEmpty()) { throw new co.edu.udea.bookingnow.domain.exception.CredencialesInvalidasException(); }
        if (cuenta.get().getEstado() != co.edu.udea.bookingnow.domain.model.EstadoCuenta.ACTIVA) { throw new co.edu.udea.bookingnow.domain.exception.CuentaNoHabilitadaException(); }
        return cuenta.get();
    }
}
