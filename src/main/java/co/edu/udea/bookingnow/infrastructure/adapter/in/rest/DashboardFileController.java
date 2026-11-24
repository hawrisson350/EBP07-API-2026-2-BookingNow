package co.edu.udea.bookingnow.infrastructure.adapter.in.rest;

import java.nio.file.Path;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/** Serves the admin dashboard from a mounted file so UI-only updates avoid rebuilding the API jar. */
@RestController
@ConditionalOnProperty(name = "bookingnow.dashboard.file")
public class DashboardFileController {
    private final Path dashboardFile;

    public DashboardFileController(@Value("${bookingnow.dashboard.file}") String dashboardFile) {
        this.dashboardFile = Path.of(dashboardFile).toAbsolutePath().normalize();
    }

    @GetMapping(value = "/", produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity<Resource> home() {
        FileSystemResource resource = new FileSystemResource(dashboardFile);
        if (!resource.exists() || !resource.isReadable()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok()
                .cacheControl(CacheControl.noStore())
                .contentType(MediaType.TEXT_HTML)
                .body(resource);
    }
}
