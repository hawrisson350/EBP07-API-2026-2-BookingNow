package co.edu.udea.bookingnow.infrastructure.adapter.out.persistence;

import co.edu.udea.bookingnow.application.port.out.ReporteRepositoryPort;
import co.edu.udea.bookingnow.domain.model.ReporteReservaFila;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public class ReporteRepositoryAdapter implements ReporteRepositoryPort {
    private final ReservaJpaRepository reservas;

    public ReporteRepositoryAdapter(ReservaJpaRepository reservas) {
        this.reservas = reservas;
    }

    @Override
    public List<ReporteReservaFila> consultarReservas(Long idProveedor, Long idNegocio, Long idServicio,
            Long idRecurso, String estado, LocalDate desde, LocalDate hasta) {
        String filtroEstado = estado == null || estado.isBlank()
                ? ""
                : estado.strip().toUpperCase(Locale.ROOT);
        return reservas.consultarParaReporte(idProveedor, idNegocio, idServicio, idRecurso,
                filtroEstado, desde, hasta).stream()
                .map(r -> new ReporteReservaFila(r.getIdReserva(), r.getFecha(), r.getHora(),
                        r.getCliente().getNombreUsuario(), r.getServicio().getNombre(),
                        r.getRecurso() == null ? "" : r.getRecurso().getNombre(),
                        r.getServicio().getNegocio().getNombre(), r.getEstado()))
                .toList();
    }
}
