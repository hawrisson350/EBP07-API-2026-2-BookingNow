package co.edu.udea.bookingnow.application.service;
import co.edu.udea.bookingnow.application.port.in.servicio.*;
import co.edu.udea.bookingnow.application.port.out.*;
import co.edu.udea.bookingnow.domain.model.*;
import co.edu.udea.bookingnow.domain.exception.*;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.List;
@Service
public class ServicioService implements RegistrarServicioUseCase, ListarServiciosUseCase {
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
        String descripcion = v.texto(c.descripcion(), "descripcion", 2000, true);
        if (c.duracionMinutos() == null || c.duracionMinutos() <= 0) { v.error("duracionMinutos", "Debe ser un entero mayor a cero"); }
        if (c.precio() == null || c.precio().signum() < 0 || c.precio().compareTo(new java.math.BigDecimal("9999999999.99")) > 0
                || c.precio().stripTrailingZeros().scale() > 2) { v.error("precio", "Debe estar entre 0 y 9999999999.99 con hasta dos decimales"); }
        String imagen = v.url(c.imagenReferencia(), "imagenReferencia");
        v.terminar();
        return servicios.guardar(new Servicio(null, negocioId, nombre, c.duracionMinutos(), c.precio(),
                descripcion, imagen, "ACTIVO", Instant.now()));
    }
    public List<Servicio> listar(Long proveedorId, Long negocioId) {
        verificarPropietario(proveedorId, negocioId);
        return servicios.listarPorNegocio(negocioId);
    }
    public List<Servicio> listarParaAdministrador(Long negocioId) {
        negocios.obtenerPorId(negocioId).orElseThrow(() -> new NoEncontradoException("Negocio no encontrado"));
        return servicios.listarPorNegocio(negocioId);
    }
}
