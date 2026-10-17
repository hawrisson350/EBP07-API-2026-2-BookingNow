package co.edu.udea.bookingnow.application.service;

import co.edu.udea.bookingnow.application.port.in.negocio.*;
import co.edu.udea.bookingnow.application.port.out.*;
import co.edu.udea.bookingnow.domain.exception.*;
import co.edu.udea.bookingnow.domain.model.HorarioAtencion;
import org.springframework.stereotype.Service;
import java.util.*;

@Service public class HorarioAtencionService implements ConfigurarHorariosUseCase,ListarHorariosUseCase {
 private final NegocioRepositoryPort negocios; private final HorarioAtencionRepositoryPort horarios;
 public HorarioAtencionService(NegocioRepositoryPort n,HorarioAtencionRepositoryPort h){negocios=n;horarios=h;}
 private void propietario(Long proveedor,Long negocio){var n=negocios.obtenerPorId(negocio).orElseThrow(()->new NoEncontradoException("Negocio no encontrado")); if(!n.idProveedor().equals(proveedor))throw new AccesoDenegadoException("El negocio no pertenece al proveedor autenticado");}
 public List<HorarioAtencion> configurar(Long proveedor,Long negocio,List<HorarioAtencionCommand> commands){propietario(proveedor,negocio);if(commands==null)throw new IllegalArgumentException("Envía la lista de horarios");Set<java.time.DayOfWeek> dias=new HashSet<>();List<HorarioAtencion> datos=new ArrayList<>();var v=new ValidacionRegistro();for(int i=0;i<commands.size();i++){var h=commands.get(i);if(h==null){v.error("horarios["+i+"]","El horario es obligatorio");continue;}if(h.dia()==null){v.error("horarios["+i+"].dia","Selecciona un día");continue;}if(!dias.add(h.dia()))v.error("horarios["+i+"].dia","No repitas el día");if(h.horaApertura()==null)v.error("horarios["+i+"].horaApertura","La hora de apertura es obligatoria");if(h.horaCierre()==null)v.error("horarios["+i+"].horaCierre","La hora de cierre es obligatoria");if(h.horaApertura()!=null&&h.horaCierre()!=null&&!h.horaCierre().isAfter(h.horaApertura()))v.error("horarios["+i+"].horaCierre","Debe ser posterior a la apertura");datos.add(new HorarioAtencion(null,negocio,h.dia(),h.horaApertura(),h.horaCierre()));}v.terminar();return horarios.reemplazarHorarios(negocio,List.copyOf(datos));}
 public List<HorarioAtencion> listar(Long proveedor,Long negocio){propietario(proveedor,negocio);return horarios.listarPorNegocio(negocio);}
}
