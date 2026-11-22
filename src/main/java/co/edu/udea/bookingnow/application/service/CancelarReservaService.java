package co.edu.udea.bookingnow.application.service;

import co.edu.udea.bookingnow.application.port.in.reserva.CancelarReservaUseCase;
import co.edu.udea.bookingnow.application.port.out.PublicarEmailPort;
import co.edu.udea.bookingnow.application.port.out.ReservaRepositoryPort;
import co.edu.udea.bookingnow.domain.exception.AccesoDenegadoException;
import co.edu.udea.bookingnow.domain.exception.ConflictoException;
import co.edu.udea.bookingnow.domain.exception.NoEncontradoException;
import co.edu.udea.bookingnow.domain.model.Reserva;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Locale;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CancelarReservaService implements CancelarReservaUseCase {
    private static final ZoneId ZONA_PROYECTO = ZoneId.of("America/Bogota");
    private final ReservaRepositoryPort reservas;
    private final PublicarEmailPort email;

    public CancelarReservaService(ReservaRepositoryPort reservas, PublicarEmailPort email) {
        this.reservas = reservas;
        this.email = email;
    }

    @Override
    @Transactional
    public Reserva cancelar(Long idReserva, Long idActor, String rolActor, String motivo) {
        var detalle = reservas.obtenerParaCancelar(idReserva)
                .orElseThrow(() -> new NoEncontradoException("Reserva no encontrada"));
        String rol = rolActor == null ? "" : rolActor.toUpperCase(Locale.ROOT);
        boolean clientePropietario = "CLIENTE".equals(rol) && idActor.equals(detalle.idCliente());
        boolean proveedorPropietario = "PROVEEDOR".equals(rol) && idActor.equals(detalle.idProveedor());
        boolean administrador = "ADMINISTRADOR".equals(rol);
        if (!clientePropietario && !proveedorPropietario && !administrador) {
            throw new AccesoDenegadoException("Solo el cliente propietario, el proveedor del negocio o un administrador pueden cancelar esta reserva");
        }

        String estado = detalle.estado().toUpperCase(Locale.ROOT);
        if (!estado.equals("ACTIVA") && !estado.equals("CONFIRMADA")) {
            throw new ConflictoException("La reserva no está en un estado que permita cancelación");
        }
        LocalDateTime cita = LocalDateTime.of(detalle.fecha(), detalle.hora());
        if (LocalDateTime.now(ZONA_PROYECTO).plusHours(2).isAfter(cita)) {
            throw new ConflictoException("La reserva solo se puede cancelar hasta dos horas antes de la cita");
        }
        if ((proveedorPropietario || administrador) && (motivo == null || motivo.isBlank())) {
            throw new IllegalArgumentException("Indica el motivo de la cancelación");
        }

        String estadoNuevo = proveedorPropietario ? "CANCELADA_POR_EL_PROVEEDOR" : "CANCELADA";
        Reserva cancelada = reservas.actualizarEstado(idReserva, estadoNuevo);
        String asunto = "Actualización de tu reserva";
        String contenido = clientePropietario
                ? "Tu reserva ha sido cancelada correctamente."
                : "Tu reserva en " + detalle.nombreNegocio() + " para el servicio " + detalle.nombreServicio()
                    + " fue cancelada por " + (proveedorPropietario ? "el proveedor" : "un administrador")
                    + ". Motivo: " + motivo + ". Disculpa las molestias; puedes comunicarte con el negocio para reprogramar.";
        email.publicar(detalle.correoCliente(), asunto, contenido);
        return cancelada;
    }
}
