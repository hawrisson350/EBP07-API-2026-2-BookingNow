package co.edu.udea.bookingnow.infrastructure.adapter.in.rest;

import co.edu.udea.bookingnow.application.port.in.servicio.*;
import co.edu.udea.bookingnow.infrastructure.adapter.in.rest.dto.ServicioCatalogoResponse;
import co.edu.udea.bookingnow.infrastructure.adapter.in.rest.dto.ServicioResumenResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/api/servicios")
public class ServicioCatalogoController {
 private final BuscarServiciosUseCase buscar; private final ObtenerServicioDetalleUseCase obtener;
 public ServicioCatalogoController(BuscarServiciosUseCase b,ObtenerServicioDetalleUseCase o){buscar=b;obtener=o;}
 @GetMapping public PaginaResponse buscar(@RequestParam(required=false) String nombre,@RequestParam(required=false) String tipo,@RequestParam(defaultValue="0") int pagina,@RequestParam(defaultValue="20") int tamano){var p=buscar.buscar(nombre,tipo,pagina,tamano);return new PaginaResponse(p.contenido().stream().map(ServicioResumenResponse::from).toList(),p.pagina(),p.tamano(),p.totalElementos(),p.totalPaginas());}
 @GetMapping("/{idServicio}") public ResponseEntity<ServicioCatalogoResponse> obtener(@PathVariable Long idServicio){return ResponseEntity.of(obtener.obtener(idServicio).map(ServicioCatalogoResponse::from));}
 public record PaginaResponse(java.util.List<ServicioResumenResponse> contenido,int pagina,int tamano,long totalElementos,int totalPaginas){}
}
