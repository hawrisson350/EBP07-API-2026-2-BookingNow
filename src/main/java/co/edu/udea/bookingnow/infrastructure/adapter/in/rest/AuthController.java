package co.edu.udea.bookingnow.infrastructure.adapter.in.rest;
import co.edu.udea.bookingnow.application.port.in.proveedor.IniciarSesionProveedorUseCase;
import co.edu.udea.bookingnow.application.port.in.cliente.IniciarSesionClienteUseCase;
import co.edu.udea.bookingnow.application.port.in.auth.CredencialesCommand;

import co.edu.udea.bookingnow.infrastructure.adapter.in.rest.dto.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class AuthController {
    private final IniciarSesionClienteUseCase clientes;
    private final IniciarSesionProveedorUseCase proveedores;
    private final SecurityContextRepository contexts;

    public AuthController(IniciarSesionClienteUseCase clientes, IniciarSesionProveedorUseCase proveedores,
            SecurityContextRepository contexts) {
        this.clientes = clientes;
        this.proveedores = proveedores;
        this.contexts = contexts;
    }

    @GetMapping("/api/auth/csrf")
    public CsrfToken csrf(CsrfToken token) { return token; }

    @PostMapping("/api/clientes/login")
    public ClienteResponse cliente(@RequestBody CredencialesCommand command,
            HttpServletRequest request, HttpServletResponse response) {
        var cliente = clientes.iniciarSesionCliente(command);
        iniciarSesion("cliente:" + cliente.getIdCliente(), "ROLE_CLIENTE", request, response);
        return ClienteResponse.from(cliente);
    }

    @PostMapping("/api/proveedores/login")
    public ProveedorResponse proveedor(@RequestBody CredencialesCommand command,
            HttpServletRequest request, HttpServletResponse response) {
        var proveedor = proveedores.iniciarSesionProveedor(command);
        iniciarSesion("proveedor:" + proveedor.getIdProveedor(), "ROLE_PROVEEDOR", request, response);
        return ProveedorResponse.from(proveedor);
    }

    private void iniciarSesion(String principal, String role, HttpServletRequest request, HttpServletResponse response) {
        if (request.getSession(false) != null) { request.changeSessionId(); }
        var authentication = UsernamePasswordAuthenticationToken.authenticated(principal, null,
                List.of(new SimpleGrantedAuthority(role)));
        var context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
        contexts.saveContext(context, request, response);
    }
}
