package co.edu.udea.bookingnow.domain.state;

import co.edu.udea.bookingnow.domain.exception.ConflictoException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class EstadoReservaTest {
    @Test
    void reservaConfirmadaCanceladaPorClienteQuedaCancelada() {
        var siguiente = EstadoReserva.desde("CONFIRMADA").cancelar(ActorCancelacion.CLIENTE);

        assertThat(siguiente.codigo()).isEqualTo("CANCELADA");
    }

    @Test
    void reservaActivaCanceladaPorProveedorConservaElActorEnElEstado() {
        var siguiente = EstadoReserva.desde("ACTIVA").cancelar(ActorCancelacion.PROVEEDOR);

        assertThat(siguiente.codigo()).isEqualTo("CANCELADA_POR_EL_PROVEEDOR");
    }

    @Test
    void administradorCancelaComoCancelacionGeneral() {
        var siguiente = EstadoReserva.desde("ACTIVA").cancelar(ActorCancelacion.ADMINISTRADOR);

        assertThat(siguiente.codigo()).isEqualTo("CANCELADA");
    }

    @ParameterizedTest
    @ValueSource(strings = {"PENDIENTE_CONFIRMACION", "COMPLETADA", "CANCELADA",
            "CANCELADA_POR_EL_PROVEEDOR", "NO_ASISTIO"})
    void estadoNoCancelableRechazaLaTransicion(String codigo) {
        assertThatThrownBy(() -> EstadoReserva.desde(codigo).cancelar(ActorCancelacion.CLIENTE))
                .isInstanceOf(ConflictoException.class)
                .hasMessage("La reserva no está en un estado que permita cancelación");
    }

    @Test
    void estadoDesconocidoSeRechazaSinErrorInterno() {
        assertThatThrownBy(() -> EstadoReserva.desde("ESTADO_INESPERADO"))
                .isInstanceOf(ConflictoException.class);
    }
}
