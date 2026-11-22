package co.edu.udea.bookingnow.infrastructure.adapter.in.rest;

import co.edu.udea.bookingnow.infrastructure.config.LocalAuditLog;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class AuditoriaController {
    private final LocalAuditLog audit;
    public AuditoriaController(LocalAuditLog audit) { this.audit = audit; }

    @GetMapping("/api/admin/auditoria")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public Map<String, List<String>> consultar(@RequestParam(defaultValue = "200") int limite) {
        return Map.of("peticiones", audit.tail("requests.jsonl", limite),
                "iniciosSesion", audit.tail("login-attempts.jsonl", limite));
    }
}
