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

    public ProveedorService(ProveedorRepositoryPort repository, ContrasenaPort contrasenaPort) {
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
        if (command == null || command.nombreUsuario() == null || command.contrasena() == null
                || command.contrasena().getBytes(java.nio.charset.StandardCharsets.UTF_8).length > 72) {
            throw new co.edu.udea.bookingnow.domain.exception.CredencialesInvalidasException();
        }
        var cuenta = repository.obtenerPorNombreUsuario(command.nombreUsuario().strip().toLowerCase(java.util.Locale.ROOT));
        boolean valida = contrasenaPort.coincide(command.contrasena(),
                cuenta.map(Proveedor::getContrasenaHash).orElse(hashInexistente));
        if (!valida || cuenta.isEmpty()) { throw new co.edu.udea.bookingnow.domain.exception.CredencialesInvalidasException(); }
        return cuenta.get();
    }
}
