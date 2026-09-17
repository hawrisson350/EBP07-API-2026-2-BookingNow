package co.edu.udea.bookingnow.domain.exception;
import java.util.Map;
public class ValidacionException extends IllegalArgumentException {
    private final Map<String, String> campos;
    public ValidacionException(Map<String, String> campos) {
        super("Revisa los campos indicados");
        this.campos = Map.copyOf(campos);
    }
    public Map<String, String> getCampos() { return campos; }
}
