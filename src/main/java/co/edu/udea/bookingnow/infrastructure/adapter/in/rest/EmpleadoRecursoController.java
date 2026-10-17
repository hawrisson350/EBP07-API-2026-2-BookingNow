package co.edu.udea.bookingnow.infrastructure.adapter.in.rest;

import co.edu.udea.bookingnow.application.port.in.empleado.*;
import co.edu.udea.bookingnow.application.port.in.recurso.*;
import co.edu.udea.bookingnow.domain.model.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController @RequestMapping("/api/negocios/{idNegocio}")
public class EmpleadoRecursoController {
 private final RegistrarEmpleadoUseCase registrarEmpleado; private final ListarEmpleadosUseCase listarEmpleados; private final RegistrarRecursoUseCase registrarRecurso; private final ListarRecursosUseCase listarRecursos;
 public EmpleadoRecursoController(RegistrarEmpleadoUseCase re,ListarEmpleadosUseCase le,RegistrarRecursoUseCase rr,ListarRecursosUseCase lr){registrarEmpleado=re;listarEmpleados=le;registrarRecurso=rr;listarRecursos=lr;}
 private Long proveedor(Authentication a){return Long.valueOf(a.getName().split(":")[1]);}
 @PostMapping("/empleados") @ResponseStatus(HttpStatus.CREATED) @PreAuthorize("authentication.name.startsWith('proveedor:')")
 public Empleado empleado(Authentication a,@PathVariable Long idNegocio,@RequestBody RegistrarEmpleadoCommand c){return registrarEmpleado.registrar(proveedor(a),idNegocio,c);}
 @GetMapping("/empleados") @PreAuthorize("authentication.name.startsWith('proveedor:')")
 public List<Empleado> empleados(Authentication a,@PathVariable Long idNegocio){return listarEmpleados.listar(proveedor(a),idNegocio);}
 @PostMapping("/recursos") @ResponseStatus(HttpStatus.CREATED) @PreAuthorize("authentication.name.startsWith('proveedor:')")
 public Recurso recurso(Authentication a,@PathVariable Long idNegocio,@RequestBody RegistrarRecursoCommand c){return registrarRecurso.registrar(proveedor(a),idNegocio,c);}
 @GetMapping("/recursos") @PreAuthorize("authentication.name.startsWith('proveedor:')")
 public List<Recurso> recursos(Authentication a,@PathVariable Long idNegocio){return listarRecursos.listar(proveedor(a),idNegocio);}
}
