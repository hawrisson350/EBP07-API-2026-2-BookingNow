package co.edu.udea.bookingnow.infrastructure.adapter.out.persistence;
import jakarta.persistence.*;
@Entity
@Table(name = "servicios")
public class ServicioJpaEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idServicio;
    @ManyToOne(fetch = FetchType.LAZY, optional = false) @JoinColumn(name = "id_negocio", nullable = false)
    private NegocioJpaEntity negocio;
    @Column(nullable = false)
    private String nombre;
    @Column(nullable = false)
    private Integer duracionMinutos;
    @Column(nullable = false, precision = 12, scale = 2)
    private java.math.BigDecimal precio;
    @Column(nullable = false, length = 2000)
    private String descripcion;
    @Column(name = "imagen_referencia_base64", columnDefinition = "text")
    private String imagenReferenciaBase64;
    @Column(nullable = false, length = 16)
    private String estado;
    @Column(nullable = false)
    private java.time.Instant fechaRegistro;
    public Long getIdServicio() { return idServicio; }
    public void setIdServicio(Long valor) { this.idServicio = valor; }
    public NegocioJpaEntity getNegocio() { return negocio; }
    public void setNegocio(NegocioJpaEntity valor) { this.negocio = valor; }
    public String getNombre() { return nombre; }
    public void setNombre(String valor) { this.nombre = valor; }
    public Integer getDuracionMinutos() { return duracionMinutos; }
    public void setDuracionMinutos(Integer valor) { this.duracionMinutos = valor; }
    public java.math.BigDecimal getPrecio() { return precio; }
    public void setPrecio(java.math.BigDecimal valor) { this.precio = valor; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String valor) { this.descripcion = valor; }
    public String getImagenReferenciaBase64() { return imagenReferenciaBase64; }
    public void setImagenReferenciaBase64(String valor) { this.imagenReferenciaBase64 = valor; }
    public String getEstado() { return estado; }
    public void setEstado(String valor) { this.estado = valor; }
    public java.time.Instant getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(java.time.Instant valor) { this.fechaRegistro = valor; }
}
