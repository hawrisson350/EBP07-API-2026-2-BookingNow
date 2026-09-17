package co.edu.udea.bookingnow.infrastructure.adapter.out.persistence;

import jakarta.persistence.*;

@Entity
@Table(name = "proveedores")
public class ProveedorJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProveedor;
    @Column(nullable = false, unique = true)
    private String correo;
    @Column(nullable = false, unique = true, length = 50)
    private String nombreUsuario;
    @Column(nullable = false)
    private String razonSocial;
    @Column(nullable = false, unique = true, length = 11)
    private String nit;
    @Column(nullable = false)
    private String contrasenaHash;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 16)
    private co.edu.udea.bookingnow.domain.model.EstadoCuenta estado = co.edu.udea.bookingnow.domain.model.EstadoCuenta.ACTIVA;

    @Column(nullable = false, unique = true)
    private String razonSocialNormalizada;

    public ProveedorJpaEntity() {}

    public ProveedorJpaEntity(Long idProveedor, String correo, String nombreUsuario, String razonSocial, String nit, String contrasenaHash) {
        this.idProveedor = idProveedor;
        this.correo = correo;
        this.nombreUsuario = nombreUsuario;
        this.razonSocial = razonSocial;
        this.razonSocialNormalizada = razonSocial.strip().replaceAll("\\s+", " ").toLowerCase(java.util.Locale.ROOT);
        this.nit = nit;
        this.contrasenaHash = contrasenaHash;
    }

    public Long getIdProveedor() { return idProveedor; }
    public void setIdProveedor(Long idProveedor) { this.idProveedor = idProveedor; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getNombreUsuario() { return nombreUsuario; }
    public void setNombreUsuario(String nombreUsuario) { this.nombreUsuario = nombreUsuario; }

    public String getRazonSocial() { return razonSocial; }
    public void setRazonSocial(String razonSocial) { this.razonSocial = razonSocial; }

    public String getNit() { return nit; }
    public void setNit(String nit) { this.nit = nit; }

    public String getContrasenaHash() { return contrasenaHash; }
    public void setContrasenaHash(String contrasenaHash) { this.contrasenaHash = contrasenaHash; }
    public co.edu.udea.bookingnow.domain.model.EstadoCuenta getEstado() { return estado; }
    public void setEstado(co.edu.udea.bookingnow.domain.model.EstadoCuenta estado) { this.estado = estado; }
}
