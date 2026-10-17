package co.edu.udea.bookingnow.infrastructure.adapter.out.persistence;

import jakarta.persistence.*;

@Entity @Table(name="empleados",uniqueConstraints=@UniqueConstraint(name="uq_empleado_negocio_documento",columnNames={"id_negocio","documento"}))
public class EmpleadoJpaEntity {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long idEmpleado;
 @ManyToOne(fetch=FetchType.LAZY,optional=false) @JoinColumn(name="id_negocio",nullable=false) private NegocioJpaEntity negocio;
 @Column(nullable=false,length=255) private String nombres;
 @Column(nullable=false,length=32) private String documento;
 @Column(nullable=false,length=25) private String telefono;
 @Column(nullable=false,length=255) private String correo;
 public Long getIdEmpleado(){return idEmpleado;} public void setIdEmpleado(Long v){idEmpleado=v;}
 public NegocioJpaEntity getNegocio(){return negocio;} public void setNegocio(NegocioJpaEntity v){negocio=v;}
 public String getNombres(){return nombres;} public void setNombres(String v){nombres=v;}
 public String getDocumento(){return documento;} public void setDocumento(String v){documento=v;}
 public String getTelefono(){return telefono;} public void setTelefono(String v){telefono=v;}
 public String getCorreo(){return correo;} public void setCorreo(String v){correo=v;}
}
