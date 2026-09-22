package co.edu.udea.bookingnow.infrastructure.adapter.out.persistence;
import jakarta.persistence.*;
@Embeddable
public class MultimediaJpaValue {
    @Column(name = "contenido_base64", nullable = false, columnDefinition = "text") private String base64;
    @Column(nullable = false, length = 16) private String tipo;
    @Column(nullable = false) private Integer orden;
    public MultimediaJpaValue() {}
    public MultimediaJpaValue(String base64, String tipo, Integer orden) { this.base64 = base64; this.tipo = tipo; this.orden = orden; }
    public String getBase64() { return base64; }
    public String getTipo() { return tipo; }
    public Integer getOrden() { return orden; }
}
