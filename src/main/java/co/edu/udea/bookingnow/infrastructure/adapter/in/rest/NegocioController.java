package co.edu.udea.bookingnow.infrastructure.adapter.in.rest;

import co.edu.udea.bookingnow.application.port.in.negocio.*;
import co.edu.udea.bookingnow.infrastructure.adapter.in.rest.dto.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

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
    public record MisNegociosResponse(java.util.List<NegocioResponse> negocios) {}
    @GetMapping
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public java.util.List<NegocioResponse> listar() {
        return listar.listar().stream().map(NegocioResponse::from).toList();
    }
    @GetMapping("/mio")
    @PreAuthorize("authentication.name.startsWith('proveedor:')")
    public MisNegociosResponse mio(Authentication auth) {
        return new MisNegociosResponse(obtener.obtenerMisNegocios(id(auth)).stream().map(NegocioResponse::from).toList());
    }
    @PostMapping @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("authentication.name.startsWith('proveedor:')")
    public RegistroResponse<NegocioResponse> registrar(Authentication auth, @RequestBody RegistrarNegocioRequest request) {
        return new RegistroResponse<>("El negocio fue creado exitosamente", NegocioResponse.from(registrar.registrar(id(auth), request.toCommand())));
    }
}
