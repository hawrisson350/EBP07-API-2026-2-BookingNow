package co.edu.udea.bookingnow.infrastructure.adapter.in.rest;

import co.edu.udea.bookingnow.application.port.in.servicio.*;
import co.edu.udea.bookingnow.domain.model.AsignacionServicio;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/api/negocios/{idNegocio}/servicios/{idServicio}/asignaciones")
public class AsignacionServicioController {
 private final AsignarPersonalYRecursosUseCase asignar;
 public AsignacionServicioController(AsignarPersonalYRecursosUseCase a){asignar=a;}
 @PutMapping @PreAuthorize("authentication.name.startsWith('proveedor:')")
 public AsignacionServicio asignar(Authentication auth,@PathVariable Long idNegocio,@PathVariable Long idServicio,@RequestBody AsignarPersonalYRecursosCommand c){Long id=Long.valueOf(auth.getName().split(":")[1]);return asignar.asignar(id,idNegocio,idServicio,c);}
}
