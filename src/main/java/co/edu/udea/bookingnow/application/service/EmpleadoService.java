package co.edu.udea.bookingnow.application.service;

import co.edu.udea.bookingnow.application.port.in.empleado.*;
import co.edu.udea.bookingnow.application.port.out.*;
import co.edu.udea.bookingnow.domain.exception.*;
import co.edu.udea.bookingnow.domain.model.Empleado;
import org.springframework.stereotype.Service;
import java.util.*;

@Service public class EmpleadoService implements RegistrarEmpleadoUseCase,ListarEmpleadosUseCase {
 private final NegocioRepositoryPort negocios; private final EmpleadoRepositoryPort empleados;
 public EmpleadoService(NegocioRepositoryPort n,EmpleadoRepositoryPort e){negocios=n;empleados=e;}
 private void propietario(Long proveedor,Long negocio){var n=negocios.obtenerPorId(negocio).orElseThrow(()->new NoEncontradoException("Negocio no encontrado")); if(!n.idProveedor().equals(proveedor)) throw new AccesoDenegadoException("El negocio no pertenece al proveedor autenticado");}
 public Empleado registrar(Long proveedor,Long negocio,RegistrarEmpleadoCommand c){propietario(proveedor,negocio); if(c==null)throw new IllegalArgumentException("Los datos del empleado son obligatorios"); var v=new ValidacionRegistro(); String nombres=v.texto(c.nombres(),"nombres",255,true); String doc=v.texto(c.documento(),"documento",32,true); if(doc!=null&&!doc.matches("[0-9]{5,15}"))v.error("documento","Ingresa un documento de 5 a 15 dígitos"); String tel=v.texto(c.telefono(),"telefono",25,true); if(tel!=null&&(!tel.matches("[+0-9() -]+")||tel.replaceAll("[^0-9]","").length()<7||tel.replaceAll("[^0-9]","").length()>15))v.error("telefono","Ingresa un teléfono válido"); String correo=v.texto(c.correo(),"correo",255,true); if(correo!=null){try{correo=ValidacionCuenta.correo(correo);}catch(IllegalArgumentException ex){v.error("correo","Correo inválido");}} if(doc!=null&&empleados.existeDocumentoEnNegocio(negocio,doc))throw new ConflictoException("Ya existe un empleado con ese documento en el negocio"); v.terminar(); return empleados.guardar(new Empleado(null,negocio,nombres,doc,tel,correo));}
 public List<Empleado> listar(Long proveedor,Long negocio){propietario(proveedor,negocio);return empleados.listarPorNegocio(negocio);}
}
