package co.edu.udea.bookingnow.infrastructure.adapter.in.rest.dto;

import co.edu.udea.bookingnow.domain.model.Servicio;
import co.edu.udea.bookingnow.domain.model.ServicioDetalle;

public record ServicioCatalogoResponse(Long idServicio,Long idNegocio,String nombre,String tipoServicio,Integer duracionMinutos,java.math.BigDecimal precio,String descripcion,String imagenReferenciaBase64,java.util.List<String> imagenesBase64,String estado,java.time.Instant fechaRegistro,String nombreNegocio,String categoriaNegocio){
 public static ServicioCatalogoResponse from(Servicio s,String nombreNegocio,String categoriaNegocio){return new ServicioCatalogoResponse(s.idServicio(),s.idNegocio(),s.nombre(),s.tipoServicio(),s.duracionMinutos(),s.precio(),s.descripcion(),s.imagenReferenciaBase64(),s.imagenesBase64(),s.estado(),s.fechaRegistro(),nombreNegocio,categoriaNegocio);}
 public static ServicioCatalogoResponse from(ServicioDetalle d){return from(d.servicio(),d.nombreNegocio(),d.categoriaNegocio());}
}
