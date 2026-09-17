package co.edu.udea.bookingnow.infrastructure.adapter.out.persistence;

import jakarta.persistence.*;

@Entity
@Table(name = "clientes")
public class ClienteJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCliente;
    @Column(nullable = false, unique = true)
    private String correo;
    @Column(nullable = false, unique = true, length = 50)
    private String nombreUsuario;
    @Column(nullable = false)
    private String contrasenaHash;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 16)
    private co.edu.udea.bookingnow.domain.model.EstadoCuenta estado = co.edu.udea.bookingnow.domain.model.EstadoCuenta.ACTIVA;

    public ClienteJpaEntity() {}

    public ClienteJpaEntity(Long idCliente, String correo, String nombreUsuario, String contrasenaHash) {
        this.idCliente = idCliente;
        this.correo = correo;
        this.nombreUsuario = nombreUsuario;
        this.contrasenaHash = contrasenaHash;
    }

    public Long getIdCliente() { return idCliente; }
    public void setIdCliente(Long idCliente) { this.idCliente = idCliente; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getNombreUsuario() { return nombreUsuario; }
    public void setNombreUsuario(String nombreUsuario) { this.nombreUsuario = nombreUsuario; }

    public String getContrasenaHash() { return contrasenaHash; }
    public void setContrasenaHash(String contrasenaHash) { this.contrasenaHash = contrasenaHash; }
    public co.edu.udea.bookingnow.domain.model.EstadoCuenta getEstado() { return estado; }
    public void setEstado(co.edu.udea.bookingnow.domain.model.EstadoCuenta estado) { this.estado = estado; }
}
