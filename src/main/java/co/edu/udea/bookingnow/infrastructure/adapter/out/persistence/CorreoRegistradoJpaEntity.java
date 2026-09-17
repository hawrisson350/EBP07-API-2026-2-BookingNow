package co.edu.udea.bookingnow.infrastructure.adapter.out.persistence;
import jakarta.persistence.*;
@Entity
@Table(name = "correos_registrados")
public class CorreoRegistradoJpaEntity {
    @Id @Column(length = 255) private String correo;
    @Column(nullable = false, length = 16) private String tipo;
    protected CorreoRegistradoJpaEntity() {}
    public CorreoRegistradoJpaEntity(String correo, String tipo) { this.correo = correo; this.tipo = tipo; }
    public String getTipo() { return tipo; }
}
