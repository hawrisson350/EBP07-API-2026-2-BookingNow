package co.edu.udea.bookingnow.infrastructure.adapter.in.rest;

import co.edu.udea.bookingnow.application.port.in.reserva.*;
import co.edu.udea.bookingnow.domain.model.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
public class ReservaController {
 private final RegistrarReservaUseCase registrar; private final ConsultarDisponibilidadUseCase consultar;
 public ReservaController(RegistrarReservaUseCase r,ConsultarDisponibilidadUseCase c){registrar=r;consultar=c;}
 @GetMapping("/api/servicios/{idServicio}/disponibilidad") public List<OpcionDisponibilidad> disponibilidad(@PathVariable Long idServicio,@RequestParam LocalDate fecha){return consultar.consultar(idServicio,fecha);}
 @PostMapping("/api/reservas") @ResponseStatus(HttpStatus.CREATED) @PreAuthorize("authentication.name.startsWith('cliente:')")
 public Reserva registrar(Authentication auth,@RequestBody RegistrarReservaCommand command){Long idCliente=Long.valueOf(auth.getName().split(":")[1]);return registrar.registrar(idCliente,command);}
}
