package co.edu.udea.bookingnow.infrastructure.adapter.in.rest;
import co.edu.udea.bookingnow.application.port.in.servicio.*;
import co.edu.udea.bookingnow.domain.model.Servicio;
import co.edu.udea.bookingnow.infrastructure.adapter.in.rest.dto.RegistroResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
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
    public RegistroResponse<Servicio> registrar(Authentication auth, @PathVariable Long idNegocio, @RequestBody RegistrarServicioCommand command) {
        return new RegistroResponse<>("El servicio fue registrado exitosamente", registrar.registrar(id(auth), idNegocio, command));
    }
    @GetMapping
    @PreAuthorize("hasRole('ADMINISTRADOR') or authentication.name.startsWith('proveedor:')")
    public List<Servicio> listar(Authentication auth, @PathVariable Long idNegocio) {
        boolean administrador = auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMINISTRADOR"));
        return administrador ? listar.listarParaAdministrador(idNegocio) : listar.listar(id(auth), idNegocio);
    }
}
