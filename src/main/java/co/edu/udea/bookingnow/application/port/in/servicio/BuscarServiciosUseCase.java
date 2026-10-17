package co.edu.udea.bookingnow.application.port.in.servicio;

public interface BuscarServiciosUseCase { PaginaServicios buscar(String nombre,String tipo,int pagina,int tamano); }
