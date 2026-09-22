package co.edu.udea.bookingnow.infrastructure.adapter.out.persistence;
import jakarta.persistence.*;
@Entity
@Table(name = "negocios")
public class NegocioJpaEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idNegocio;
    @ManyToOne(fetch = FetchType.LAZY, optional = false) @JoinColumn(name = "id_proveedor", nullable = false)
    private ProveedorJpaEntity proveedor;
    @Column(nullable = false)
    private String nombre;
    @Column(nullable = false)
    private String correo;
    @Column(nullable = false, length = 25)
    private String numContacto;
    @Column(length = 500)
    private String direccion;
    @Column(nullable = false, length = 100)
    private String categoria;
    @Column(nullable = false)
    private boolean modalidadVirtual;
    @Column(name = "foto_principal_base64", columnDefinition = "text")
    private String fotoPrincipalBase64;
    @Column(nullable = false)
    private java.time.Instant fechaRegistro;
    @ElementCollection @CollectionTable(name = "negocio_multimedia", joinColumns = @JoinColumn(name = "id_negocio")) @OrderBy("orden ASC")
    private java.util.List<MultimediaJpaValue> galeria;
    public Long getIdNegocio() { return idNegocio; }
    public void setIdNegocio(Long valor) { this.idNegocio = valor; }
    public ProveedorJpaEntity getProveedor() { return proveedor; }
    public void setProveedor(ProveedorJpaEntity valor) { this.proveedor = valor; }
    public String getNombre() { return nombre; }
    public void setNombre(String valor) { this.nombre = valor; }
    public String getCorreo() { return correo; }
    public void setCorreo(String valor) { this.correo = valor; }
    public String getNumContacto() { return numContacto; }
    public void setNumContacto(String valor) { this.numContacto = valor; }
    public String getDireccion() { return direccion; }
    public void setDireccion(String valor) { this.direccion = valor; }
    public String getCategoria() { return categoria; }
    public void setCategoria(String valor) { this.categoria = valor; }
    public boolean getModalidadVirtual() { return modalidadVirtual; }
    public void setModalidadVirtual(boolean valor) { this.modalidadVirtual = valor; }
    public String getFotoPrincipalBase64() { return fotoPrincipalBase64; }
    public void setFotoPrincipalBase64(String valor) { this.fotoPrincipalBase64 = valor; }
    public java.time.Instant getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(java.time.Instant valor) { this.fechaRegistro = valor; }
    public java.util.List<MultimediaJpaValue> getGaleria() { return galeria; }
    public void setGaleria(java.util.List<MultimediaJpaValue> valor) { this.galeria = valor; }
}
