package co.edu.udea.bookingnow.application.service;
import co.edu.udea.bookingnow.domain.exception.ValidacionException;
import java.net.URI;
import java.util.LinkedHashMap;
import java.util.Map;
final class ValidacionRegistro {
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
    String url(String valor, String campo) {
        String limpio = texto(valor, campo, 2048, false);
        if (limpio != null) {
            try {
                var uri = URI.create(limpio);
                if (!"https".equalsIgnoreCase(uri.getScheme()) || uri.getHost() == null || uri.getUserInfo() != null) {
                    error(campo, "Debe ser una URL HTTPS válida sin credenciales");
                }
            } catch (IllegalArgumentException e) { error(campo, "URL inválida"); }
        }
        return limpio;
    }
    void terminar() { if (!errors.isEmpty()) { throw new ValidacionException(errors); } }
}
