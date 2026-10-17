package co.edu.udea.bookingnow.infrastructure.adapter.out.persistence;

import jakarta.persistence.*;
import java.time.*;

@Entity @Table(name="horarios_atencion",uniqueConstraints=@UniqueConstraint(name="uq_horario_negocio_dia",columnNames={"id_negocio","dia_semana"}))
public class HorarioAtencionJpaEntity {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long idHorario;
 @ManyToOne(fetch=FetchType.LAZY,optional=false) @JoinColumn(name="id_negocio",nullable=false) private NegocioJpaEntity negocio;
 @Enumerated(EnumType.STRING) @Column(name="dia_semana",nullable=false,length=12) private DayOfWeek dia;
 @Column(name="hora_apertura",nullable=false) private LocalTime horaApertura;
 @Column(name="hora_cierre",nullable=false) private LocalTime horaCierre;
 public Long getIdHorario(){return idHorario;} public void setIdHorario(Long v){idHorario=v;} public NegocioJpaEntity getNegocio(){return negocio;} public void setNegocio(NegocioJpaEntity v){negocio=v;} public DayOfWeek getDia(){return dia;} public void setDia(DayOfWeek v){dia=v;} public LocalTime getHoraApertura(){return horaApertura;} public void setHoraApertura(LocalTime v){horaApertura=v;} public LocalTime getHoraCierre(){return horaCierre;} public void setHoraCierre(LocalTime v){horaCierre=v;}
}
