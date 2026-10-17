package co.edu.udea.bookingnow.application.service;

import co.edu.udea.bookingnow.application.port.in.recurso.*;
import co.edu.udea.bookingnow.application.port.out.*;
import co.edu.udea.bookingnow.domain.exception.*;
import co.edu.udea.bookingnow.domain.model.*;
import org.springframework.stereotype.Service;
import java.util.*;

@Service public class RecursoService implements RegistrarRecursoUseCase,ListarRecursosUseCase {
 private static final Set<String> ESTADOS=Set.of("DISPONIBLE","NO_DISPONIBLE","MANTENIMIENTO");
 private final NegocioRepositoryPort negocios; private final RecursoRepositoryPort recursos;
 public RecursoService(NegocioRepositoryPort n,RecursoRepositoryPort r){negocios=n;recursos=r;}
 private void propietario(Long proveedor,Long negocio){var n=negocios.obtenerPorId(negocio).orElseThrow(()->new NoEncontradoException("Negocio no encontrado")); if(!n.idProveedor().equals(proveedor)) throw new AccesoDenegadoException("El negocio no pertenece al proveedor autenticado");}
 public Recurso registrar(Long proveedor,Long negocio,RegistrarRecursoCommand c){propietario(proveedor,negocio); if(c==null) throw new IllegalArgumentException("Los datos del recurso son obligatorios"); var v=new ValidacionRegistro(); String nombre=v.texto(c.nombre(),"nombre",255,true); String descripcion=v.texto(c.descripcion(),"descripcion",2000,true); if(c.cantidad()==null||c.cantidad()<=0)v.error("cantidad","La cantidad debe ser mayor que cero"); String estado=c.estado()==null?"":c.estado().strip().toUpperCase(Locale.ROOT); if(!ESTADOS.contains(estado))v.error("estado","Usa DISPONIBLE, NO_DISPONIBLE o MANTENIMIENTO"); v.terminar(); return recursos.guardar(new Recurso(null,negocio,nombre,c.cantidad(),estado,descripcion));}
 public List<Recurso> listar(Long proveedor,Long negocio){propietario(proveedor,negocio);return recursos.listarPorNegocio(negocio);}
}
