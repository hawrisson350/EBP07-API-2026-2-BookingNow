package co.edu.udea.bookingnow.application.port.in.servicio;

import co.edu.udea.bookingnow.domain.model.ServicioResumen;
import java.util.List;

public record PaginaServicios(List<ServicioResumen> contenido,int pagina,int tamano,long totalElementos,int totalPaginas) {}
