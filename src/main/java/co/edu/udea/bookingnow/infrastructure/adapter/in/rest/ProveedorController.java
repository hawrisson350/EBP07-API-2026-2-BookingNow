package co.edu.udea.bookingnow.infrastructure.adapter.in.rest;
import co.edu.udea.bookingnow.application.port.in.proveedor.RegistrarProveedorCommand;
import co.edu.udea.bookingnow.application.port.in.proveedor.ObtenerProveedorUseCase;
import co.edu.udea.bookingnow.application.port.in.proveedor.ObtenerProveedoresUseCase;
import co.edu.udea.bookingnow.application.port.in.proveedor.EliminarProveedorUseCase;
import co.edu.udea.bookingnow.application.port.in.proveedor.CrearProveedorUseCase;

import co.edu.udea.bookingnow.infrastructure.adapter.in.rest.dto.ProveedorResponse;
import co.edu.udea.bookingnow.infrastructure.adapter.in.rest.dto.RegistroResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("/api/proveedores")
public class ProveedorController {
    private final CrearProveedorUseCase crear;
    private final ObtenerProveedorUseCase obtener;
    private final ObtenerProveedoresUseCase listar;
    private final EliminarProveedorUseCase eliminar;

    public ProveedorController(CrearProveedorUseCase crear, ObtenerProveedorUseCase obtener,
            ObtenerProveedoresUseCase listar, EliminarProveedorUseCase eliminar) {
        this.crear = crear;
        this.obtener = obtener;
        this.listar = listar;
        this.eliminar = eliminar;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RegistroResponse<ProveedorResponse> crear(@RequestBody RegistrarProveedorCommand command) {
        return new RegistroResponse<>("La cuenta fue creada exitosamente", ProveedorResponse.from(crear.crearProveedor(command)));
    }

    @GetMapping
    public List<ProveedorResponse> listar() {
        return listar.obtenerProveedores().stream().map(ProveedorResponse::from).toList();
    }

    @PreAuthorize("authentication.name == 'proveedor:' + #id")
    @GetMapping("/{id}")
    public ResponseEntity<ProveedorResponse> obtener(@PathVariable Long id) {
        return ResponseEntity.of(obtener.obtenerProveedor(id).map(ProveedorResponse::from));
    }

    @PreAuthorize("authentication.name == 'proveedor:' + #id")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        eliminar.eliminarProveedor(id);
    }
}
