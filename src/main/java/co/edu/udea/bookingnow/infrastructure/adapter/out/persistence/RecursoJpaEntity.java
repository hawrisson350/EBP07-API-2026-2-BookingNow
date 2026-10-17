package co.edu.udea.bookingnow.infrastructure.adapter.out.persistence;

import jakarta.persistence.*;

@Entity @Table(name="recursos")
public class RecursoJpaEntity {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long idRecurso;
 @ManyToOne(fetch=FetchType.LAZY,optional=false) @JoinColumn(name="id_negocio",nullable=false) private NegocioJpaEntity negocio;
 @Column(nullable=false,length=255) private String nombre;
 @Column(nullable=false) private Integer cantidad;
 @Column(nullable=false,length=24) private String estado;
 @Column(nullable=false,length=2000) private String descripcion;
 public Long getIdRecurso(){return idRecurso;} public void setIdRecurso(Long v){idRecurso=v;}
 public NegocioJpaEntity getNegocio(){return negocio;} public void setNegocio(NegocioJpaEntity v){negocio=v;}
 public String getNombre(){return nombre;} public void setNombre(String v){nombre=v;}
 public Integer getCantidad(){return cantidad;} public void setCantidad(Integer v){cantidad=v;}
 public String getEstado(){return estado;} public void setEstado(String v){estado=v;}
 public String getDescripcion(){return descripcion;} public void setDescripcion(String v){descripcion=v;}
}
