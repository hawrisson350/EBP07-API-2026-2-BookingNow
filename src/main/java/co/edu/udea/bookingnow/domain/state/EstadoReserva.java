package co.edu.udea.bookingnow.domain.state;

import co.edu.udea.bookingnow.domain.exception.ConflictoException;
import java.util.Locale;

/**
 * State del ciclo de vida de una reserva. La implementación concreta decide
 * si puede cancelarse y cuál es el siguiente estado.
 */
public sealed interface EstadoReserva
        permits EstadoCancelableReserva, EstadoNoCancelableReserva {
    String codigo();

    EstadoReserva cancelar(ActorCancelacion actor);

    /** El flujo actual registra las reservas directamente como confirmadas. */
    static EstadoReserva confirmada() {
        return new EstadoCancelableReserva("CONFIRMADA");
    }

    /** Convierte el valor persistido a un comportamiento de estado del dominio. */
    static EstadoReserva desde(String estado) {
        if (estado == null || estado.isBlank()) {
            throw new ConflictoException("La reserva no está en un estado que permita cancelación");
        }
        String codigo = estado.trim().toUpperCase(Locale.ROOT);
        return switch (codigo) {
            case "ACTIVA", "CONFIRMADA" -> new EstadoCancelableReserva(codigo);
            case "PENDIENTE_CONFIRMACION", "COMPLETADA", "CANCELADA",
                    "CANCELADA_POR_EL_PROVEEDOR", "NO_ASISTIO" -> new EstadoNoCancelableReserva(codigo);
            default -> throw new ConflictoException("La reserva no está en un estado que permita cancelación");
        };
    }
}
