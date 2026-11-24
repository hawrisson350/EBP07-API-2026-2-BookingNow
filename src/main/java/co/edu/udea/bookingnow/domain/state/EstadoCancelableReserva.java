package co.edu.udea.bookingnow.domain.state;

import java.util.Objects;

/** Estado ACTIVA o CONFIRMADA: admite cancelación antes de la cita. */
final class EstadoCancelableReserva implements EstadoReserva {
    private final String codigo;

    EstadoCancelableReserva(String codigo) {
        this.codigo = Objects.requireNonNull(codigo);
    }

    @Override
    public String codigo() {
        return codigo;
    }

    @Override
    public EstadoReserva cancelar(ActorCancelacion actor) {
        Objects.requireNonNull(actor, "El actor que cancela es obligatorio");
        String siguiente = actor == ActorCancelacion.PROVEEDOR
                ? "CANCELADA_POR_EL_PROVEEDOR"
                : "CANCELADA";
        return new EstadoNoCancelableReserva(siguiente);
    }
}
