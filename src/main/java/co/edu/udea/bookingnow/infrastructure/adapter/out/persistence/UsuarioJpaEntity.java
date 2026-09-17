package co.edu.udea.bookingnow.infrastructure.adapter.out.persistence;

import co.edu.udea.bookingnow.domain.model.EstadoCuenta;
import co.edu.udea.bookingnow.domain.model.RolUsuario;
import jakarta.persistence.*;

@Entity
@Table(name = "usuarios")
public class UsuarioJpaEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario") private Long idUsuario;
    @Column(nullable = false, unique = true, length = 255) private String correo;
    @Column(name = "nombre_usuario", nullable = false, unique = true, length = 50) private String nombreUsuario;
    @Column(name = "contrasena_hash", nullable = false, length = 255) private String contrasenaHash;
    @Enumerated(EnumType.STRING) @Column(nullable = false, length = 32) private RolUsuario rol;
    @Enumerated(EnumType.STRING) @Column(nullable = false, length = 16) private EstadoCuenta estado;

    protected UsuarioJpaEntity() { }
    public Long getIdUsuario() { return idUsuario; }
    public String getCorreo() { return correo; }
    public String getNombreUsuario() { return nombreUsuario; }
    public String getContrasenaHash() { return contrasenaHash; }
    public RolUsuario getRol() { return rol; }
    public EstadoCuenta getEstado() { return estado; }
}
