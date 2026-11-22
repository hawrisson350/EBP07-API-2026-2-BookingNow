package co.edu.udea.bookingnow.infrastructure.adapter.in.rest;

import co.edu.udea.bookingnow.application.port.in.reserva.*;
import co.edu.udea.bookingnow.domain.model.*;
import co.edu.udea.bookingnow.infrastructure.adapter.in.rest.dto.ReservaConsultaResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
public class ReservaController {
 private final RegistrarReservaUseCase registrar; private final ConsultarDisponibilidadUseCase consultar;
 private final ConsultarReservasActivasUseCase consultarActivas; private final ConsultarHistorialReservasUseCase consultarHistorial;
 private final CancelarReservaUseCase cancelar;
 public ReservaController(RegistrarReservaUseCase r,ConsultarDisponibilidadUseCase c,ConsultarReservasActivasUseCase ca,ConsultarHistorialReservasUseCase ch,CancelarReservaUseCase cancelar){registrar=r;consultar=c;consultarActivas=ca;consultarHistorial=ch;this.cancelar=cancelar;}
 @GetMapping("/api/servicios/{idServicio}/disponibilidad") public List<OpcionDisponibilidad> disponibilidad(@PathVariable Long idServicio,@RequestParam LocalDate fecha){return consultar.consultar(idServicio,fecha);}
 @PostMapping("/api/reservas") @ResponseStatus(HttpStatus.CREATED) @PreAuthorize("authentication.name.startsWith('cliente:')")
 public Reserva registrar(Authentication auth,@RequestBody RegistrarReservaCommand command){Long idCliente=Long.valueOf(auth.getName().split(":")[1]);return registrar.registrar(idCliente,command);}
 @GetMapping("/api/reservas/mias/activas") @PreAuthorize("authentication.name.startsWith('cliente:')")
 public List<ReservaConsultaResponse> misReservasActivas(Authentication auth){Long idCliente=Long.valueOf(auth.getName().split(":")[1]);return consultarActivas.consultar(idCliente).stream().map(ReservaConsultaResponse::from).toList();}
 @GetMapping("/api/reservas/mias/historial") @PreAuthorize("authentication.name.startsWith('cliente:')")
 public List<ReservaConsultaResponse> historial(Authentication auth){Long idCliente=Long.valueOf(auth.getName().split(":")[1]);return consultarHistorial.consultarHistorial(idCliente).stream().map(ReservaConsultaResponse::from).toList();}
 @PostMapping("/api/reservas/{idReserva}/cancelacion") @PreAuthorize("hasAnyRole('CLIENTE','PROVEEDOR','ADMINISTRADOR')")
 public Reserva cancelar(Authentication auth,@PathVariable Long idReserva,@RequestBody(required=false) CancelarReservaCommand command){
  String[] identidad=auth.getName().split(":",2);String rol=switch(identidad[0]){case "cliente"->"CLIENTE";case "proveedor"->"PROVEEDOR";default->"ADMINISTRADOR";};
  return cancelar.cancelar(idReserva,Long.valueOf(identidad[1]),rol,command==null?null:command.motivo());
 }
}
