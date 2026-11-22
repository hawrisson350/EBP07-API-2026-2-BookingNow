package co.edu.udea.bookingnow.infrastructure.adapter.in.rest;

import co.edu.udea.bookingnow.application.port.in.proveedor.IniciarSesionProveedorUseCase;
import co.edu.udea.bookingnow.application.port.in.cliente.IniciarSesionClienteUseCase;
import co.edu.udea.bookingnow.application.port.in.auth.CredencialesCommand;
import co.edu.udea.bookingnow.infrastructure.adapter.in.rest.dto.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.web.bind.annotation.*;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import jakarta.servlet.http.HttpServletRequest;
import co.edu.udea.bookingnow.infrastructure.config.LocalAuditLog;
import java.util.Map;

@RestController
public class AuthController {
    private final IniciarSesionClienteUseCase clientes;
    private final IniciarSesionProveedorUseCase proveedores;
    private final co.edu.udea.bookingnow.application.port.in.auth.IniciarSesionUseCase autenticar;
    private final JwtEncoder encoder;
    private final String issuer;
    private final String audience;
    private final long ttl;
    private final LocalAuditLog audit;

    public AuthController(IniciarSesionClienteUseCase clientes, IniciarSesionProveedorUseCase proveedores,
            co.edu.udea.bookingnow.application.port.in.auth.IniciarSesionUseCase autenticar, JwtEncoder encoder, @Value("${security.jwt.issuer}") String issuer,
            @Value("${security.jwt.audience}") String audience, @Value("${security.jwt.ttl-seconds}") long ttl,
            LocalAuditLog audit) {
        if (ttl <= 0) { throw new IllegalArgumentException("La duracion JWT debe ser positiva"); }
        this.autenticar = autenticar;
        this.clientes = clientes;
        this.proveedores = proveedores;
        this.encoder = encoder;
        this.issuer = issuer;
        this.audience = audience;
        this.ttl = ttl;
        this.audit = audit;
    }

    @PostMapping("/api/clientes/login")
    public LoginResponse<ClienteResponse> cliente(@RequestBody CredencialesCommand command, HttpServletRequest request) {
        try {
            var cuenta = clientes.iniciarSesionCliente(command);
            logLogin(request, command.correo(), true, "CLIENTE");
            return emitir("cliente:" + cuenta.getIdCliente(), "CLIENTE", ClienteResponse.from(cuenta), List.of("CLIENTE"));
        } catch (RuntimeException ex) { logLogin(request, command.correo(), false, "CLIENTE"); throw ex; }
    }

    @PostMapping("/api/proveedores/login")
    public LoginResponse<ProveedorResponse> proveedor(@RequestBody CredencialesCommand command, HttpServletRequest request) {
        try {
            var cuenta = proveedores.iniciarSesionProveedor(command);
            logLogin(request, command.correo(), true, "PROVEEDOR");
            return emitir("proveedor:" + cuenta.getIdProveedor(), "PROVEEDOR", ProveedorResponse.from(cuenta), List.of("PROVEEDOR"));
        } catch (RuntimeException ex) { logLogin(request, command.correo(), false, "PROVEEDOR"); throw ex; }
    }

    @PostMapping("/api/auth/login")
    public LoginResponse<co.edu.udea.bookingnow.domain.model.IdentidadAutenticada> login(@RequestBody CredencialesCommand command, HttpServletRequest request) {
        try {
            var cuenta = autenticar.iniciarSesion(command);
            logLogin(request, command.correo(), true, cuenta.rol());
            return emitir(cuenta.rol().equals("ADMINISTRADOR") ? "usuario:" + cuenta.id() : cuenta.rol().toLowerCase(java.util.Locale.ROOT) + ":" + cuenta.id(), cuenta.rol(), cuenta, List.copyOf(cuenta.roles()));
        } catch (RuntimeException ex) { logLogin(request, command.correo(), false, "DESCONOCIDO"); throw ex; }
    }

    private void logLogin(HttpServletRequest request, String correo, boolean success, String role) {
        String masked = "no_disponible";
        if (correo != null && !correo.isBlank()) {
            int at = correo.indexOf('@');
            masked = at > 1 ? correo.substring(0, 1) + "***" + correo.substring(at) : correo.substring(0, 1) + "***";
        }
        String forwarded = request.getHeader("X-Forwarded-For");
        audit.append("login-attempts.jsonl", Map.of("path", request.getRequestURI(),
                "result", success ? "EXITOSO" : "FALLIDO", "status", success ? 200 : 401,
                "rol", role, "usuario", masked, "remoteIp", request.getRemoteAddr(),
                "forwardedFor", forwarded == null ? "" : forwarded,
                "userAgent", request.getHeader("User-Agent") == null ? "" : request.getHeader("User-Agent")));
    }

    private <T> LoginResponse<T> emitir(String subject, String rolPrincipal, T cuenta, List<String> roles) {
        Instant now = Instant.now();
        var claims = JwtClaimsSet.builder().issuer(issuer).audience(List.of(audience))
                .claim("rol", rolPrincipal)
                .claim("roles", roles).subject(subject).issuedAt(now).expiresAt(now.plusSeconds(ttl)).id(UUID.randomUUID().toString()).build();
        String token = encoder.encode(JwtEncoderParameters.from(JwsHeader.with(MacAlgorithm.HS256).build(), claims)).getTokenValue();
        return new LoginResponse<>(token, "Bearer", ttl, cuenta);
    }
}
