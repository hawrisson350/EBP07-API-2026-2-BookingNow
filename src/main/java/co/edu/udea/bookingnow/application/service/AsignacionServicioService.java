package co.edu.udea.bookingnow.application.service;

import co.edu.udea.bookingnow.application.port.in.servicio.*;
import co.edu.udea.bookingnow.application.port.out.*;
import co.edu.udea.bookingnow.domain.exception.*;
import co.edu.udea.bookingnow.domain.model.AsignacionServicio;
import org.springframework.stereotype.Service;
import java.util.*;

@Service public class AsignacionServicioService implements AsignarPersonalYRecursosUseCase {
 private final NegocioRepositoryPort negocios; private final ServicioRepositoryPort servicios; private final EmpleadoRepositoryPort empleados; private final RecursoRepositoryPort recursos; private final AsignacionServicioRepositoryPort asignaciones;
 public AsignacionServicioService(NegocioRepositoryPort n,ServicioRepositoryPort s,EmpleadoRepositoryPort e,RecursoRepositoryPort r,AsignacionServicioRepositoryPort a){negocios=n;servicios=s;empleados=e;recursos=r;asignaciones=a;}
 public AsignacionServicio asignar(Long proveedor,Long negocio,Long servicio,AsignarPersonalYRecursosCommand c){var n=negocios.obtenerPorId(negocio).orElseThrow(()->new NoEncontradoException("Negocio no encontrado"));if(!n.idProveedor().equals(proveedor))throw new AccesoDenegadoException("El negocio no pertenece al proveedor autenticado");var s=servicios.obtenerPorId(servicio).orElseThrow(()->new NoEncontradoException("Servicio no encontrado"));if(!s.idNegocio().equals(negocio))throw new AccesoDenegadoException("El servicio no pertenece al negocio indicado");if(c==null)throw new IllegalArgumentException("Los datos de asignación son obligatorios");List<Long> emp=c.idEmpleados()==null?List.of():c.idEmpleados();List<Long> rec=c.idRecursos()==null?List.of():c.idRecursos();var v=new ValidacionRegistro();if(new HashSet<>(emp).size()!=emp.size())v.error("idEmpleados","No repitas empleados");if(new HashSet<>(rec).size()!=rec.size())v.error("idRecursos","No repitas recursos");if(c.requiereEmpleados()&&emp.isEmpty())v.error("idEmpleados","Asigna al menos un empleado si el servicio los requiere");if(c.requiereRecursos()&&rec.isEmpty())v.error("idRecursos","Asigna al menos un recurso si el servicio los requiere");var eIds=empleados.idsPorNegocio(negocio,new HashSet<>(emp));if(eIds.size()!=new HashSet<>(emp).size())v.error("idEmpleados","Todos los empleados deben pertenecer al negocio");var rIds=recursos.idsDisponiblesPorNegocio(negocio,new HashSet<>(rec));if(rIds.size()!=new HashSet<>(rec).size())v.error("idRecursos","Todos los recursos deben pertenecer al negocio y estar disponibles");v.terminar();return asignaciones.reemplazar(new AsignacionServicio(servicio,c.requiereEmpleados(),c.requiereRecursos(),List.copyOf(emp),List.copyOf(rec)));}
}
