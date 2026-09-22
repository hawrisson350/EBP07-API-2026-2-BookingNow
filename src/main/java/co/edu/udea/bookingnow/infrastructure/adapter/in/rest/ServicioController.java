package co.edu.udea.bookingnow.infrastructure.adapter.in.rest;

import co.edu.udea.bookingnow.application.port.in.servicio.*;
import co.edu.udea.bookingnow.infrastructure.adapter.in.rest.dto.RegistroResponse;
import co.edu.udea.bookingnow.infrastructure.adapter.in.rest.dto.RegistrarServicioRequest;
import co.edu.udea.bookingnow.infrastructure.adapter.in.rest.dto.ServicioResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/negocios/{idNegocio}/servicios")
public class ServicioController {
    private final RegistrarServicioUseCase registrar;
    private final ListarServiciosUseCase listar;
    public ServicioController(RegistrarServicioUseCase registrar, ListarServiciosUseCase listar) { this.registrar = registrar; this.listar = listar; }
    private Long id(Authentication auth) { return Long.valueOf(auth.getName().split(":")[1]); }
    @PostMapping @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("authentication.name.startsWith('proveedor:')")
    public RegistroResponse<ServicioResponse> registrar(Authentication auth, @PathVariable Long idNegocio, @RequestBody RegistrarServicioRequest request) {
        return new RegistroResponse<>("El servicio fue registrado exitosamente", ServicioResponse.from(registrar.registrar(id(auth), idNegocio, request.toCommand())));
    }
    @GetMapping
    @PreAuthorize("hasRole('ADMINISTRADOR') or authentication.name.startsWith('proveedor:')")
    public List<ServicioResponse> listar(Authentication auth, @PathVariable Long idNegocio) {
        boolean administrador = auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMINISTRADOR"));
        return (administrador ? listar.listarParaAdministrador(idNegocio) : listar.listar(id(auth), idNegocio))
                .stream().map(ServicioResponse::from).toList();
    }
}
