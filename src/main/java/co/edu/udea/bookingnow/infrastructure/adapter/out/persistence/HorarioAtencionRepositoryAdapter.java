package co.edu.udea.bookingnow.infrastructure.adapter.out.persistence;

import co.edu.udea.bookingnow.application.port.out.HorarioAtencionRepositoryPort;
import co.edu.udea.bookingnow.domain.model.HorarioAtencion;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Repository @Transactional(readOnly=true)
public class HorarioAtencionRepositoryAdapter implements HorarioAtencionRepositoryPort {
 private final HorarioAtencionJpaRepository repo; private final EntityManager em;
 public HorarioAtencionRepositoryAdapter(HorarioAtencionJpaRepository r,EntityManager e){repo=r;em=e;}
 @Override @Transactional public List<HorarioAtencion> reemplazarHorarios(Long id,List<HorarioAtencion> horarios){repo.deleteByNegocioIdNegocio(id);var negocio=em.getReference(NegocioJpaEntity.class,id);var entities=horarios.stream().map(h->{var e=new HorarioAtencionJpaEntity();e.setNegocio(negocio);e.setDia(h.dia());e.setHoraApertura(h.horaApertura());e.setHoraCierre(h.horaCierre());return e;}).toList();return repo.saveAllAndFlush(entities).stream().map(this::toDomain).toList();}
 @Override public List<HorarioAtencion> listarPorNegocio(Long id){return repo.findByNegocioIdNegocioOrderByDiaAsc(id).stream().map(this::toDomain).toList();}
 private HorarioAtencion toDomain(HorarioAtencionJpaEntity e){return new HorarioAtencion(e.getIdHorario(),e.getNegocio().getIdNegocio(),e.getDia(),e.getHoraApertura(),e.getHoraCierre());}
}
