package co.edu.udea.bookingnow.domain.state;

import co.edu.udea.bookingnow.domain.exception.ConflictoException;
import java.util.Objects;

/** Estado pendiente o terminal: el flujo actual no admite cancelación. */
final class EstadoNoCancelableReserva implements EstadoReserva {
    private final String codigo;

    EstadoNoCancelableReserva(String codigo) {
        this.codigo = Objects.requireNonNull(codigo);
    }

    @Override
    public String codigo() {
        return codigo;
    }

    @Override
    public EstadoReserva cancelar(ActorCancelacion actor) {
        throw new ConflictoException("La reserva no está en un estado que permita cancelación");
    }
}
