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
    @Column(name = "tipo_servicio", length = 100)
    private String tipoServicio;
    @ElementCollection
    @CollectionTable(name="servicio_imagenes", joinColumns=@JoinColumn(name="id_servicio"))
    @OrderColumn(name="orden")
    @Column(name="contenido_base64", columnDefinition="text", nullable=false)
    private java.util.List<String> imagenesBase64 = new java.util.ArrayList<>();
    @ManyToMany
    @JoinTable(name="servicio_empleados", joinColumns=@JoinColumn(name="id_servicio"), inverseJoinColumns=@JoinColumn(name="id_empleado"))
    private java.util.Set<EmpleadoJpaEntity> empleados = new java.util.HashSet<>();
    @ManyToMany
    @JoinTable(name="servicio_recursos", joinColumns=@JoinColumn(name="id_servicio"), inverseJoinColumns=@JoinColumn(name="id_recurso"))
    private java.util.Set<RecursoJpaEntity> recursos = new java.util.HashSet<>();
    @Column(name="requiere_empleados",nullable=false)
    private boolean requiereEmpleados;
    @Column(name="requiere_recursos",nullable=false)
    private boolean requiereRecursos;
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
    public String getTipoServicio() { return tipoServicio; }
    public void setTipoServicio(String valor) { this.tipoServicio = valor; }
    public java.util.List<String> getImagenesBase64() { return imagenesBase64; }
    public void setImagenesBase64(java.util.List<String> valor) { this.imagenesBase64 = valor == null ? new java.util.ArrayList<>() : new java.util.ArrayList<>(valor); }
    public java.util.Set<EmpleadoJpaEntity> getEmpleados() { return empleados; }
    public void setEmpleados(java.util.Set<EmpleadoJpaEntity> valor) { this.empleados = valor; }
    public java.util.Set<RecursoJpaEntity> getRecursos() { return recursos; }
    public void setRecursos(java.util.Set<RecursoJpaEntity> valor) { this.recursos = valor; }
    public boolean isRequiereEmpleados() { return requiereEmpleados; }
    public void setRequiereEmpleados(boolean valor) { this.requiereEmpleados = valor; }
    public boolean isRequiereRecursos() { return requiereRecursos; }
    public void setRequiereRecursos(boolean valor) { this.requiereRecursos = valor; }
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
