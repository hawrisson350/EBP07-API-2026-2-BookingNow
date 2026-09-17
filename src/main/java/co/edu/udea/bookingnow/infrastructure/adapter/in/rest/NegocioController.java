package co.edu.udea.bookingnow.infrastructure.adapter.in.rest;
import co.edu.udea.bookingnow.application.port.in.negocio.*;
import co.edu.udea.bookingnow.domain.model.Negocio;
import co.edu.udea.bookingnow.infrastructure.adapter.in.rest.dto.RegistroResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
@RestController
@RequestMapping("/api/negocios")
public class NegocioController {
    private final RegistrarNegocioUseCase registrar;
    private final ObtenerMiNegocioUseCase obtener;
    private final ListarNegociosUseCase listar;
    public NegocioController(RegistrarNegocioUseCase registrar, ObtenerMiNegocioUseCase obtener, ListarNegociosUseCase listar) {
        this.registrar = registrar; this.obtener = obtener; this.listar = listar;
    }
    private Long id(Authentication auth) { return Long.valueOf(auth.getName().split(":")[1]); }
    public record MiNegocioResponse(boolean puedeRegistrar, Negocio negocio) {}
    @GetMapping
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public java.util.List<Negocio> listar() { return listar.listar(); }
    @GetMapping("/mio")
    @PreAuthorize("authentication.name.startsWith('proveedor:')")
    public MiNegocioResponse mio(Authentication auth) {
        var negocio = obtener.obtenerMiNegocio(id(auth));
        return new MiNegocioResponse(negocio.isEmpty(), negocio.orElse(null));
    }
    @PostMapping @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("authentication.name.startsWith('proveedor:')")
    public RegistroResponse<Negocio> registrar(Authentication auth, @RequestBody RegistrarNegocioCommand command) {
        return new RegistroResponse<>("El negocio fue creado exitosamente", registrar.registrar(id(auth), command));
    }
}
