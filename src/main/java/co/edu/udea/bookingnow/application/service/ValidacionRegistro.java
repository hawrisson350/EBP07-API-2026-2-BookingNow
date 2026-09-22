package co.edu.udea.bookingnow.application.service;
import co.edu.udea.bookingnow.domain.exception.ValidacionException;
import java.net.URI;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
final class ValidacionRegistro {
    private static final int MAXIMO_BYTES_MULTIMEDIA = 5 * 1024 * 1024;
    private static final Pattern DATA_URL = Pattern.compile("^data:([a-z]+/[a-z0-9.+-]+);base64,([A-Za-z0-9+/]*={0,2})$");
    private final Map<String,String> errors = new LinkedHashMap<>();
    void error(String campo, String mensaje) { errors.put(campo, mensaje); }
    String texto(String valor, String campo, int maximo, boolean obligatorio) {
        if (valor == null || valor.isBlank()) {
            if (obligatorio) { error(campo, "Campo obligatorio"); }
            return null;
        }
        String limpio = valor.strip();
        if (limpio.length() > maximo) { error(campo, "Máximo " + maximo + " caracteres"); }
        return limpio;
    }
    String base64(String valor, String campo, String tipo) {
        if (valor == null || valor.isBlank()) return null;
        String limpio = valor.strip();
        // Base64 ocupa como máximo 4/3 del archivo; esta comprobación evita decodificar entradas absurdas.
        if (limpio.length() > ((MAXIMO_BYTES_MULTIMEDIA + 2L) / 3L) * 4L + 128L) {
            error(campo, "La multimedia no puede superar 5 MB"); return limpio;
        }
        var coincidencia = DATA_URL.matcher(limpio);
        if (!coincidencia.matches()) { error(campo, "Usa una data URL Base64 válida"); return limpio; }
        String mime = coincidencia.group(1).toLowerCase(java.util.Locale.ROOT);
        Set<String> permitidos = "VIDEO".equals(tipo)
                ? Set.of("video/mp4", "video/webm")
                : Set.of("image/jpeg", "image/png", "image/webp", "image/gif");
        if (!permitidos.contains(mime)) { error(campo, "El tipo de archivo no corresponde a " + tipo); return limpio; }
        try {
            if (Base64.getDecoder().decode(coincidencia.group(2)).length > MAXIMO_BYTES_MULTIMEDIA) {
                error(campo, "La multimedia no puede superar 5 MB");
            }
        } catch (IllegalArgumentException e) { error(campo, "Base64 inválido"); }
        return limpio;
    }
    void terminar() { if (!errors.isEmpty()) { throw new ValidacionException(errors); } }
}
