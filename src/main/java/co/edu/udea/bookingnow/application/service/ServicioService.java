package co.edu.udea.bookingnow.application.service;
import co.edu.udea.bookingnow.application.port.in.servicio.*;
import co.edu.udea.bookingnow.application.port.out.*;
import co.edu.udea.bookingnow.domain.model.*;
import co.edu.udea.bookingnow.domain.exception.*;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.List;
@Service
public class ServicioService implements RegistrarServicioUseCase, ListarServiciosUseCase, BuscarServiciosUseCase, ObtenerServicioDetalleUseCase {
    private final NegocioRepositoryPort negocios;
    private final ServicioRepositoryPort servicios;
    public ServicioService(NegocioRepositoryPort negocios, ServicioRepositoryPort servicios) { this.negocios = negocios; this.servicios = servicios; }
    private void verificarPropietario(Long proveedorId, Long negocioId) {
        var negocio = negocios.obtenerPorId(negocioId).orElseThrow(() -> new NoEncontradoException("Negocio no encontrado"));
        if (!negocio.idProveedor().equals(proveedorId)) { throw new AccesoDenegadoException("El negocio no pertenece al proveedor autenticado"); }
    }
    public Servicio registrar(Long proveedorId, Long negocioId, RegistrarServicioCommand c) {
        verificarPropietario(proveedorId, negocioId);
        if (c == null) { throw new IllegalArgumentException("Los datos del servicio son obligatorios"); }
        var v = new ValidacionRegistro();
        String nombre = v.texto(c.nombre(), "nombre", 255, true);
        String tipo = v.texto(c.tipoServicio(), "tipoServicio", 100, false);
        String descripcion = v.texto(c.descripcion(), "descripcion", 2000, true);
        if (c.duracionMinutos() == null || c.duracionMinutos() <= 0) { v.error("duracionMinutos", "Debe ser un entero mayor a cero"); }
        if (c.precio() == null || c.precio().signum() < 0 || c.precio().compareTo(new java.math.BigDecimal("9999999999.99")) > 0
                || c.precio().stripTrailingZeros().scale() > 2) { v.error("precio", "Debe estar entre 0 y 9999999999.99 con hasta dos decimales"); }
        var imagenes = new java.util.ArrayList<String>();
        if (c.imagenesBase64() != null) {
            if (c.imagenesBase64().size() > 20) v.error("imagenesBase64", "Máximo 20 imágenes");
            for (int i=0; i<Math.min(c.imagenesBase64().size(),20); i++) {
                String contenido = c.imagenesBase64().get(i);
                if (contenido == null || contenido.isBlank()) v.error("imagenesBase64["+i+"]", "La imagen es obligatoria");
                else imagenes.add(v.base64(contenido, "imagenesBase64["+i+"]", "IMAGEN"));
            }
        }
        String imagen = v.base64(c.imagenReferenciaBase64(), "imagenReferenciaBase64", "IMAGEN");
        if (imagenes.isEmpty() && imagen != null) imagenes.add(imagen);
        if (imagen == null && !imagenes.isEmpty()) imagen = imagenes.get(0);
        v.terminar();
        return servicios.guardar(new Servicio(null, negocioId, nombre, c.duracionMinutos(), c.precio(),
                descripcion, imagen, "ACTIVO", Instant.now(), tipo, imagenes));
    }
    public List<Servicio> listar(Long proveedorId, Long negocioId) {
        verificarPropietario(proveedorId, negocioId);
        return servicios.listarPorNegocio(negocioId);
    }
    public List<Servicio> listarParaAdministrador(Long negocioId) {
        negocios.obtenerPorId(negocioId).orElseThrow(() -> new NoEncontradoException("Negocio no encontrado"));
        return servicios.listarPorNegocio(negocioId);
    }
    public PaginaServicios buscar(String nombre, String tipo, int pagina, int tamano) {
        if (pagina < 0) throw new IllegalArgumentException("La página debe ser mayor o igual a cero");
        if (tamano < 1 || tamano > 100) throw new IllegalArgumentException("El tamaño debe estar entre 1 y 100");
        String filtroNombre = nombre == null || nombre.isBlank() ? null : nombre.strip();
        String filtroTipo = tipo == null || tipo.isBlank() ? null : tipo.strip();
        return servicios.buscar(filtroNombre, filtroTipo, pagina, tamano);
    }
    public java.util.Optional<ServicioDetalle> obtener(Long id) {
        return servicios.obtenerPorId(id).map(servicio -> {
            var negocio = negocios.obtenerPorId(servicio.idNegocio()).orElseThrow(() -> new NoEncontradoException("Negocio del servicio no encontrado"));
            return new ServicioDetalle(servicio, negocio.nombre(), negocio.categoria());
        });
    }
}
