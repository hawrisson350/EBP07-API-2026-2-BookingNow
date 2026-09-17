package co.edu.udea.bookingnow.infrastructure.adapter.in.rest;
import co.edu.udea.bookingnow.application.port.in.cliente.RegistrarClienteCommand;
import co.edu.udea.bookingnow.application.port.in.cliente.ObtenerClienteUseCase;
import co.edu.udea.bookingnow.application.port.in.cliente.ObtenerClientesUseCase;
import co.edu.udea.bookingnow.application.port.in.cliente.EliminarClienteUseCase;
import co.edu.udea.bookingnow.application.port.in.cliente.CrearClienteUseCase;

import co.edu.udea.bookingnow.infrastructure.adapter.in.rest.dto.ClienteResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {
    private final CrearClienteUseCase crear;
    private final ObtenerClienteUseCase obtener;
    private final ObtenerClientesUseCase listar;
    private final EliminarClienteUseCase eliminar;

    public ClienteController(CrearClienteUseCase crear, ObtenerClienteUseCase obtener,
            ObtenerClientesUseCase listar, EliminarClienteUseCase eliminar) {
        this.crear = crear;
        this.obtener = obtener;
        this.listar = listar;
        this.eliminar = eliminar;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClienteResponse crear(@RequestBody RegistrarClienteCommand command) {
        return ClienteResponse.from(crear.crearCliente(command));
    }

    @GetMapping
    public List<ClienteResponse> listar() {
        return listar.obtenerClientes().stream().map(ClienteResponse::from).toList();
    }

    @PreAuthorize("authentication.name == 'cliente:' + #id")
    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponse> obtener(@PathVariable Long id) {
        return ResponseEntity.of(obtener.obtenerCliente(id).map(ClienteResponse::from));
    }

    @PreAuthorize("authentication.name == 'cliente:' + #id")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id, jakarta.servlet.http.HttpServletRequest request) {
        eliminar.eliminarCliente(id);
        if (request.getSession(false) != null) { request.getSession(false).invalidate(); }
        org.springframework.security.core.context.SecurityContextHolder.clearContext();
    }
}
