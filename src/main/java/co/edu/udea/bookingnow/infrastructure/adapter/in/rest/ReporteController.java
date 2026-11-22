package co.edu.udea.bookingnow.infrastructure.adapter.in.rest;

import co.edu.udea.bookingnow.application.port.in.reporte.GenerarReporteUseCase;
import co.edu.udea.bookingnow.domain.model.ReporteArchivo;
import co.edu.udea.bookingnow.infrastructure.adapter.in.rest.dto.GenerarReporteRequest;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReporteController {
    private final GenerarReporteUseCase generar;

    public ReporteController(GenerarReporteUseCase generar) {
        this.generar = generar;
    }

    @PostMapping("/api/reportes")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR','PROVEEDOR')")
    public ResponseEntity<byte[]> generar(Authentication auth, @RequestBody GenerarReporteRequest request) {
        String[] identidad = auth.getName().split(":", 2);
        String rol = identidad[0].equals("proveedor") ? "PROVEEDOR" : "ADMINISTRADOR";
        ReporteArchivo archivo = generar.generar(Long.valueOf(identidad[1]), rol, request.toCommand());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(archivo.tipoContenido()));
        headers.setContentDisposition(ContentDisposition.attachment().filename(archivo.nombreArchivo()).build());
        headers.setContentLength(archivo.contenido().length);
        return ResponseEntity.ok().headers(headers).body(archivo.contenido());
    }
}
