package co.edu.udea.bookingnow.application.service;
import co.edu.udea.bookingnow.application.port.in.cliente.RegistrarClienteCommand;
import co.edu.udea.bookingnow.application.port.in.cliente.ObtenerClienteUseCase;
import co.edu.udea.bookingnow.application.port.in.cliente.ObtenerClientesUseCase;
import co.edu.udea.bookingnow.application.port.in.cliente.IniciarSesionClienteUseCase;
import co.edu.udea.bookingnow.application.port.in.cliente.EliminarClienteUseCase;
import co.edu.udea.bookingnow.application.port.in.auth.CredencialesCommand;
import co.edu.udea.bookingnow.application.port.in.cliente.CrearClienteUseCase;

import co.edu.udea.bookingnow.application.port.out.ClienteRepositoryPort;
import co.edu.udea.bookingnow.application.port.out.ContrasenaPort;
import co.edu.udea.bookingnow.domain.model.Cliente;
import co.edu.udea.bookingnow.domain.exception.CuentaDuplicadaException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteService implements CrearClienteUseCase, ObtenerClienteUseCase,
        ObtenerClientesUseCase, EliminarClienteUseCase, IniciarSesionClienteUseCase {
    private final ClienteRepositoryPort repository;
    private final ContrasenaPort contrasenaPort;
    private final String hashInexistente;
    private final co.edu.udea.bookingnow.application.port.out.CorreoRegistradoPort correos;

    public ClienteService(ClienteRepositoryPort repository, ContrasenaPort contrasenaPort, co.edu.udea.bookingnow.application.port.out.CorreoRegistradoPort correos) {
        this.correos = correos;
        this.repository = repository;
        this.contrasenaPort = contrasenaPort;
        this.hashInexistente = contrasenaPort.codificar(java.util.UUID.randomUUID().toString());
    }

    @Override
    public Cliente crearCliente(RegistrarClienteCommand command) {
        if (command == null) { throw new IllegalArgumentException("Los datos son obligatorios"); }
        String correo = ValidacionCuenta.correo(command.correo());
        String nombreUsuario = ValidacionCuenta.nombreUsuario(command.nombreUsuario());
        ValidacionCuenta.contrasena(command.contrasena());
        if (correos.tipoPorCorreo(correo).isPresent()) { throw new co.edu.udea.bookingnow.domain.exception.ConflictoException("El correo ya está en uso"); }
        if (repository.existeNombreUsuario(nombreUsuario)) { throw new CuentaDuplicadaException(); }
        return repository.guardar(new Cliente(null, correo, nombreUsuario, contrasenaPort.codificar(command.contrasena())));
    }

    @Override
    public List<Cliente> obtenerClientes() { return repository.obtenerTodos(); }
    @Override
    public Optional<Cliente> obtenerCliente(Long id) { return repository.obtenerPorId(id); }
    @Override
    public void eliminarCliente(Long id) { repository.eliminar(id); }
    @Override
    public Cliente iniciarSesionCliente(CredencialesCommand command) {
        ValidacionLogin.validar(command);
        var cuenta = repository.obtenerPorCorreo(command.correo().strip().toLowerCase(java.util.Locale.ROOT));
        boolean valida = contrasenaPort.coincide(command.contrasena(),
                cuenta.map(Cliente::getContrasenaHash).orElse(hashInexistente));
        if (!valida || cuenta.isEmpty()) { throw new co.edu.udea.bookingnow.domain.exception.CredencialesInvalidasException(); }
        if (cuenta.get().getEstado() != co.edu.udea.bookingnow.domain.model.EstadoCuenta.ACTIVA) { throw new co.edu.udea.bookingnow.domain.exception.CuentaNoHabilitadaException(); }
        return cuenta.get();
    }
}
