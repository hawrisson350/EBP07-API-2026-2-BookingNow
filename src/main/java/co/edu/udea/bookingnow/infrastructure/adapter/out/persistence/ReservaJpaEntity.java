package co.edu.udea.bookingnow.infrastructure.adapter.out.persistence;

import jakarta.persistence.*;
import java.time.*;

@Entity @Table(name="reservas")
public class ReservaJpaEntity {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long idReserva;
 @ManyToOne(fetch=FetchType.LAZY,optional=false) @JoinColumn(name="id_cliente",nullable=false) private ClienteJpaEntity cliente;
 @ManyToOne(fetch=FetchType.LAZY,optional=false) @JoinColumn(name="id_servicio",nullable=false) private ServicioJpaEntity servicio;
 @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="id_empleado") private EmpleadoJpaEntity empleado;
 @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="id_recurso") private RecursoJpaEntity recurso;
 @Column(nullable=false) private LocalDate fecha;
 @Column(nullable=false) private LocalTime hora;
 @Column(nullable=false,length=32) private String estado;
 @Column(name="fecha_creacion",nullable=false) private Instant fechaCreacion;
 public Long getIdReserva(){return idReserva;} public void setIdReserva(Long v){idReserva=v;} public ClienteJpaEntity getCliente(){return cliente;} public void setCliente(ClienteJpaEntity v){cliente=v;} public ServicioJpaEntity getServicio(){return servicio;} public void setServicio(ServicioJpaEntity v){servicio=v;} public EmpleadoJpaEntity getEmpleado(){return empleado;} public void setEmpleado(EmpleadoJpaEntity v){empleado=v;} public RecursoJpaEntity getRecurso(){return recurso;} public void setRecurso(RecursoJpaEntity v){recurso=v;} public LocalDate getFecha(){return fecha;} public void setFecha(LocalDate v){fecha=v;} public LocalTime getHora(){return hora;} public void setHora(LocalTime v){hora=v;} public String getEstado(){return estado;} public void setEstado(String v){estado=v;} public Instant getFechaCreacion(){return fechaCreacion;} public void setFechaCreacion(Instant v){fechaCreacion=v;}
}
