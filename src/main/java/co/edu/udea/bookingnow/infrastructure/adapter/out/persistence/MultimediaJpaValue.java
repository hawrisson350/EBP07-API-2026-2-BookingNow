package co.edu.udea.bookingnow.infrastructure.adapter.out.persistence;
import jakarta.persistence.*;
@Embeddable
public class MultimediaJpaValue {
    @Column(nullable = false, length = 2048) private String url;
    @Column(nullable = false, length = 16) private String tipo;
    @Column(nullable = false) private Integer orden;
    public MultimediaJpaValue() {}
    public MultimediaJpaValue(String url, String tipo, Integer orden) { this.url = url; this.tipo = tipo; this.orden = orden; }
    public String getUrl() { return url; }
    public String getTipo() { return tipo; }
    public Integer getOrden() { return orden; }
}
