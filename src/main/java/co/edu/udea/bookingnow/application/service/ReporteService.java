package co.edu.udea.bookingnow.application.service;

import co.edu.udea.bookingnow.application.port.in.reporte.GenerarReporteCommand;
import co.edu.udea.bookingnow.application.port.in.reporte.GenerarReporteUseCase;
import co.edu.udea.bookingnow.application.port.out.GeneradorReportePort;
import co.edu.udea.bookingnow.application.port.out.ReporteRepositoryPort;
import co.edu.udea.bookingnow.domain.exception.AccesoDenegadoException;
import co.edu.udea.bookingnow.domain.exception.ValidacionException;
import co.edu.udea.bookingnow.domain.model.ReporteArchivo;
import java.util.Locale;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class ReporteService implements GenerarReporteUseCase {
    private final ReporteRepositoryPort reportes;
    private final GeneradorReportePort generador;

    public ReporteService(ReporteRepositoryPort reportes, GeneradorReportePort generador) {
        this.reportes = reportes;
        this.generador = generador;
    }

    @Override
    public ReporteArchivo generar(Long idActor, String rolActor, GenerarReporteCommand c) {
        if (c == null || c.tipoReporte() == null || c.formato() == null) {
            throw new ValidacionException(Map.of("solicitud", "Indica el tipo de reporte y el formato"));
        }
        String tipo = c.tipoReporte().strip().toUpperCase(Locale.ROOT);
        String formato = c.formato().strip().toUpperCase(Locale.ROOT);
        String rol = rolActor == null ? "" : rolActor.toUpperCase(Locale.ROOT);
        if (!rol.equals("ADMINISTRADOR") && !rol.equals("PROVEEDOR")) {
            throw new AccesoDenegadoException("Solo administradores y proveedores pueden generar reportes");
        }
        if (!tipo.equals("RESERVAS") && !tipo.equals("USO_SERVICIOS_RECURSOS")) {
            throw new ValidacionException(Map.of("tipoReporte", "Usa RESERVAS o USO_SERVICIOS_RECURSOS"));
        }
        if (!formato.equals("PDF") && !formato.equals("EXCEL")) {
            throw new ValidacionException(Map.of("formato", "Usa PDF o EXCEL"));
        }
        if ((c.fechaDesde() == null) != (c.fechaHasta() == null)) {
            throw new ValidacionException(Map.of("fecha", "Envía fechaDesde y fechaHasta juntas"));
        }
        if (c.fechaDesde() != null && c.fechaDesde().isAfter(c.fechaHasta())) {
            throw new ValidacionException(Map.of("fechaDesde", "No puede ser posterior a fechaHasta"));
        }
        if ("PROVEEDOR".equals(rol) && idActor == null) {
            throw new AccesoDenegadoException("No se pudo identificar al proveedor autenticado");
        }
        var filas = reportes.consultarReservas("PROVEEDOR".equals(rol) ? idActor : null,
                c.idNegocio(), c.idServicio(), c.idRecurso(), c.estado(), c.fechaDesde(), c.fechaHasta());
        if (tipo.equals("USO_SERVICIOS_RECURSOS") && c.idRecurso() == null && c.idServicio() == null
                && c.fechaDesde() == null && filas.isEmpty()) {
            // Se permiten reportes vacíos; el documento incluirá encabezados y un aviso.
        }
        return generador.generar(tipo, formato, c.fechaDesde(), c.fechaHasta(), filas);
    }
}
