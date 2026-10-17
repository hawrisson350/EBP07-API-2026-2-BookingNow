package co.edu.udea.bookingnow.infrastructure.adapter.in.rest;

import co.edu.udea.bookingnow.application.port.in.negocio.*;
import co.edu.udea.bookingnow.domain.model.HorarioAtencion;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController @RequestMapping("/api/negocios/{idNegocio}/horarios")
public class HorarioAtencionController {
 private final ConfigurarHorariosUseCase configurar; private final ListarHorariosUseCase listar;
 public HorarioAtencionController(ConfigurarHorariosUseCase c,ListarHorariosUseCase l){configurar=c;listar=l;}
 private Long proveedor(Authentication a){return Long.valueOf(a.getName().split(":")[1]);}
 @PutMapping @PreAuthorize("authentication.name.startsWith('proveedor:')")
 public List<HorarioAtencion> configurar(Authentication a,@PathVariable Long idNegocio,@RequestBody List<HorarioAtencionCommand> horarios){return configurar.configurar(proveedor(a),idNegocio,horarios);}
 @GetMapping @PreAuthorize("authentication.name.startsWith('proveedor:')")
 public List<HorarioAtencion> listar(Authentication a,@PathVariable Long idNegocio){return listar.listar(proveedor(a),idNegocio);}
}
