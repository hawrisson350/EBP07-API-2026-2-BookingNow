package co.edu.udea.bookingnow.infrastructure.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/** Small local JSONL audit store for the academic EC2 deployment. */
@Component
public class LocalAuditLog {
    private final Path directory;
    private final ObjectMapper json = new ObjectMapper();
    private static final long MAX_BYTES = 10 * 1024 * 1024;

    public LocalAuditLog(@Value("${bookingnow.audit.directory:./logs}") String directory) {
        this.directory = Path.of(directory).toAbsolutePath().normalize();
    }

    public synchronized void append(String file, Map<String, Object> event) {
        try {
            Files.createDirectories(directory);
            Path target = directory.resolve(file);
            Instant now = Instant.now();
            archivePreviousSemesterIfNeeded(file, target, now);
            if (Files.exists(target) && Files.size(target) >= MAX_BYTES) {
                Path rotated = directory.resolve(file + ".1");
                Files.deleteIfExists(rotated);
                Files.move(target, rotated);
            }
            var record = new java.util.LinkedHashMap<String, Object>();
            record.put("timestamp", now.toString());
            record.putAll(event);
            Files.writeString(target, json.writeValueAsString(record) + System.lineSeparator(),
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (Exception ignored) {
            // Audit logging must never turn an application request into a 500.
        }
    }

    private void archivePreviousSemesterIfNeeded(String file, Path active, Instant now) throws Exception {
        String semester = semesterOf(now);
        Path marker = directory.resolve(file + ".semester");
        String previous = Files.exists(marker) ? Files.readString(marker).strip() : "";
        if (!previous.isBlank() && !previous.equals(semester)) {
            String stem = file.endsWith(".jsonl") ? file.substring(0, file.length() - 6) : file;
            moveToArchive(active, stem + "-" + previous + ".jsonl");
            moveToArchive(directory.resolve(file + ".1"), stem + "-" + previous + "-rotacion-1.jsonl");
        }
        Files.writeString(marker, semester, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }

    private void moveToArchive(Path source, String archiveName) throws Exception {
        if (!Files.exists(source)) return;
        Path archive = directory.resolve(archiveName);
        if (Files.exists(archive)) {
            String suffix = java.time.format.DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss")
                    .withZone(ZoneId.of("UTC")).format(Instant.now());
            archive = directory.resolve(archiveName.replace(".jsonl", "-" + suffix + ".jsonl"));
        }
        Files.move(source, archive);
    }

    private String semesterOf(Instant instant) {
        ZonedDateTime date = instant.atZone(ZoneId.of("America/Bogota"));
        return date.getYear() + "-S" + (date.getMonthValue() <= 6 ? "1" : "2");
    }

    public synchronized List<String> tail(String file, int limit) {
        try {
            Path target = directory.resolve(file);
            if (!Files.exists(target)) return List.of();
            var lines = Files.readAllLines(target);
            int from = Math.max(0, lines.size() - Math.min(Math.max(limit, 1), 500));
            return new ArrayList<>(lines.subList(from, lines.size()));
        } catch (Exception ignored) {
            return List.of();
        }
    }
}
