package co.edu.udea.bookingnow.infrastructure.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;
import java.time.LocalDate;
import java.util.List;
import java.time.Instant;

public interface ReservaJpaRepository extends JpaRepository<ReservaJpaEntity,Long> {
 @Query("select r from ReservaJpaEntity r where r.fecha = :fecha and upper(r.estado) not in ('CANCELADA','CANCELADA_POR_EL_PROVEEDOR') order by r.hora")
 List<ReservaJpaEntity> listarAgenda(@Param("fecha") LocalDate fecha);

 @Lock(LockModeType.PESSIMISTIC_WRITE)
 @Query("select r from ReservaJpaEntity r join fetch r.cliente c join fetch r.servicio s join fetch s.negocio n join fetch n.proveedor p " +
        "where r.idReserva = :idReserva")
 java.util.Optional<ReservaJpaEntity> obtenerParaCancelar(@Param("idReserva") Long idReserva);

 @Query("select r from ReservaJpaEntity r join fetch r.cliente c join fetch r.servicio s " +
        "join fetch s.negocio n left join fetch r.recurso rec " +
        "where (:idProveedor is null or n.proveedor.idProveedor = :idProveedor) " +
        "and (:idNegocio is null or n.idNegocio = :idNegocio) " +
        "and (:idServicio is null or s.idServicio = :idServicio) " +
        "and (:idRecurso is null or rec.idRecurso = :idRecurso) " +
        "and (:estado = '' or upper(r.estado) = :estado) " +
        "and r.fecha >= coalesce(:desde, r.fecha) " +
        "and r.fecha <= coalesce(:hasta, r.fecha) " +
        "order by r.fecha asc, r.hora asc")
 List<ReservaJpaEntity> consultarParaReporte(@Param("idProveedor") Long idProveedor,
        @Param("idNegocio") Long idNegocio, @Param("idServicio") Long idServicio,
        @Param("idRecurso") Long idRecurso, @Param("estado") String estado,
        @Param("desde") LocalDate desde, @Param("hasta") LocalDate hasta);

 @Query("select r from ReservaJpaEntity r join fetch r.servicio s join fetch s.negocio n " +
        "where r.cliente.idCliente = :idCliente and (r.fecha > :desde or (r.fecha = :desde and r.hora >= :desdeHora)) " +
        "and upper(r.estado) in ('ACTIVA', 'CONFIRMADA') " +
        "order by r.fecha asc, r.hora asc")
 List<ReservaJpaEntity> listarActivasPorCliente(@Param("idCliente") Long idCliente,
        @Param("desde") LocalDate desde, @Param("desdeHora") java.time.LocalTime desdeHora);

 @Query("select r from ReservaJpaEntity r join fetch r.servicio s join fetch s.negocio n " +
        "where r.cliente.idCliente = :idCliente " +
        "and upper(r.estado) in ('COMPLETADA', 'CANCELADA', 'CANCELADA_POR_EL_PROVEEDOR', 'NO_ASISTIO') " +
        "order by r.fecha desc, r.hora desc")
 List<ReservaJpaEntity> listarHistorialPorCliente(@Param("idCliente") Long idCliente);

 @Query("select r from ReservaJpaEntity r join fetch r.cliente c join fetch r.servicio s join fetch s.negocio n " +
        "where r.recordatorioProcesadoAt is null and upper(r.estado) in ('ACTIVA','CONFIRMADA') " +
        "and (r.fecha < :fecha or (r.fecha = :fecha and r.hora <= :hora)) " +
        "and (r.fecha > :hoy or (r.fecha = :hoy and r.hora >= :ahora)) " +
        "order by r.fecha, r.hora")
 List<ReservaJpaEntity> buscarRecordatoriosPendientes(@Param("fecha") LocalDate fecha,
        @Param("hora") java.time.LocalTime hora, @Param("hoy") LocalDate hoy,
        @Param("ahora") java.time.LocalTime ahora);

 @org.springframework.data.jpa.repository.Modifying
 @Query("update ReservaJpaEntity r set r.recordatorioProcesadoAt = :enviadoEn " +
        "where r.idReserva = :idReserva and r.recordatorioProcesadoAt is null " +
        "and upper(r.estado) in ('ACTIVA','CONFIRMADA')")
 int marcarRecordatorioProcesado(@Param("idReserva") Long idReserva, @Param("enviadoEn") Instant enviadoEn);
}
