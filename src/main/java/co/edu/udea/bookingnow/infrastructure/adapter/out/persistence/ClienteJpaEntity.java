package co.edu.udea.bookingnow.infrastructure.adapter.out.persistence;

import jakarta.persistence.*;

@Entity
@Table(name = "clientes")
public class ClienteJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCliente;
    @Column(nullable = false)
    private String correo;
    @Column(nullable = false, unique = true, length = 50)
    private String nombreUsuario;
    @Column(nullable = false)
    private String contrasenaHash;

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
}
