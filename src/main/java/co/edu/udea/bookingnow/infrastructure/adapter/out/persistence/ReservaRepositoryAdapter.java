package co.edu.udea.bookingnow.infrastructure.adapter.out.persistence;

import co.edu.udea.bookingnow.application.port.out.ReservaRepositoryPort;
import co.edu.udea.bookingnow.domain.model.*;
import jakarta.persistence.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;

@Repository @Transactional(readOnly=true)
public class ReservaRepositoryAdapter implements ReservaRepositoryPort {
 private final ReservaJpaRepository repo; private final EntityManager em;
 public ReservaRepositoryAdapter(ReservaJpaRepository r,EntityManager em){repo=r;this.em=em;}
 @Override @Transactional public void bloquearRecursos(Long servicio,Long empleado,Long recurso){em.find(ServicioJpaEntity.class,servicio,LockModeType.PESSIMISTIC_WRITE);if(empleado!=null)em.find(EmpleadoJpaEntity.class,empleado,LockModeType.PESSIMISTIC_WRITE);if(recurso!=null)em.find(RecursoJpaEntity.class,recurso,LockModeType.PESSIMISTIC_WRITE);}
 @Override public List<ReservaEnAgenda> listarPorFecha(LocalDate fecha){return repo.listarAgenda(fecha).stream().map(e->new ReservaEnAgenda(e.getIdReserva(),e.getServicio().getIdServicio(),e.getEmpleado()==null?null:e.getEmpleado().getIdEmpleado(),e.getRecurso()==null?null:e.getRecurso().getIdRecurso(),e.getFecha(),e.getHora(),e.getEstado(),e.getServicio().getDuracionMinutos())).toList();}
 @Override public List<ReservaConsulta> listarActivasPorCliente(Long idCliente,LocalDate desde,java.time.LocalTime desdeHora){return repo.listarActivasPorCliente(idCliente,desde,desdeHora).stream().map(this::toConsulta).toList();}
 @Override public List<ReservaConsulta> listarHistorialPorCliente(Long idCliente){return repo.listarHistorialPorCliente(idCliente).stream().map(this::toConsulta).toList();}
 @Override public List<ReservaRecordatorio> buscarRecordatoriosPendientes(java.time.LocalDateTime hasta){var ahora=java.time.LocalDateTime.now(java.time.ZoneId.of("America/Bogota"));return repo.buscarRecordatoriosPendientes(hasta.toLocalDate(),hasta.toLocalTime(),ahora.toLocalDate(),ahora.toLocalTime()).stream().map(e->new ReservaRecordatorio(e.getIdReserva(),e.getCliente().getCorreo(),e.getCliente().getNombreUsuario(),e.getServicio().getNombre(),e.getServicio().getNegocio().getNombre(),e.getFecha(),e.getHora())).toList();}
 @Override @Transactional public boolean marcarRecordatorioProcesado(Long idReserva,java.time.Instant enviadoEn){return repo.marcarRecordatorioProcesado(idReserva,enviadoEn)==1;}
 @Override public java.util.Optional<ReservaCancelacion> obtenerParaCancelar(Long idReserva){return repo.obtenerParaCancelar(idReserva).map(e->new ReservaCancelacion(e.getIdReserva(),e.getCliente().getIdCliente(),e.getServicio().getNegocio().getProveedor().getIdProveedor(),e.getCliente().getCorreo(),e.getCliente().getNombreUsuario(),e.getServicio().getNombre(),e.getServicio().getNegocio().getNombre(),e.getFecha(),e.getHora(),e.getEstado()));}
 @Override @Transactional public Reserva actualizarEstado(Long idReserva,String estado){var e=repo.findById(idReserva).orElseThrow();e.setEstado(estado);return toDomain(repo.saveAndFlush(e));}
 @Override @Transactional public Reserva guardar(Reserva r){var e=new ReservaJpaEntity();e.setCliente(em.getReference(ClienteJpaEntity.class,r.idCliente()));e.setServicio(em.getReference(ServicioJpaEntity.class,r.idServicio()));if(r.idEmpleado()!=null)e.setEmpleado(em.getReference(EmpleadoJpaEntity.class,r.idEmpleado()));if(r.idRecurso()!=null)e.setRecurso(em.getReference(RecursoJpaEntity.class,r.idRecurso()));e.setFecha(r.fecha());e.setHora(r.hora());e.setEstado(r.estado());e.setFechaCreacion(r.fechaCreacion());return toDomain(repo.saveAndFlush(e));}
 private Reserva toDomain(ReservaJpaEntity e){return new Reserva(e.getIdReserva(),e.getCliente().getIdCliente(),e.getServicio().getIdServicio(),e.getEmpleado()==null?null:e.getEmpleado().getIdEmpleado(),e.getRecurso()==null?null:e.getRecurso().getIdRecurso(),e.getFecha(),e.getHora(),e.getEstado(),e.getFechaCreacion());}
 private ReservaConsulta toConsulta(ReservaJpaEntity e){return new ReservaConsulta(e.getIdReserva(),e.getFecha(),e.getHora(),e.getServicio().getNombre(),e.getServicio().getNegocio().getNombre(),e.getEstado());}
}
