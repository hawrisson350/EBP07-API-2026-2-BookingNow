package co.edu.udea.bookingnow.application.service;
import co.edu.udea.bookingnow.application.port.in.negocio.*;
import co.edu.udea.bookingnow.application.port.out.*;
import co.edu.udea.bookingnow.domain.model.*;
import co.edu.udea.bookingnow.domain.exception.*;
import org.springframework.stereotype.Service;
import java.util.*;
import java.time.Instant;
@Service
public class NegocioService implements RegistrarNegocioUseCase, ObtenerMiNegocioUseCase, ListarNegociosUseCase {
    private final NegocioRepositoryPort negocios;
    private final ProveedorRepositoryPort proveedores;
    public NegocioService(NegocioRepositoryPort negocios, ProveedorRepositoryPort proveedores) {
        this.negocios = negocios; this.proveedores = proveedores;
    }
    public Negocio registrar(Long proveedorId, RegistrarNegocioCommand c) {
        var proveedor = proveedores.obtenerPorId(proveedorId).orElseThrow(() -> new AccesoDenegadoException("Proveedor no disponible"));
        if (proveedor.getEstado() != EstadoCuenta.ACTIVA) { throw new AccesoDenegadoException("Cuenta no habilitada"); }
        if (negocios.obtenerPorProveedor(proveedorId).isPresent()) { throw new ConflictoException("El proveedor ya tiene un negocio registrado"); }
        if (c == null) { throw new IllegalArgumentException("Los datos del negocio son obligatorios"); }
        var v = new ValidacionRegistro();
        String nombre = v.texto(c.nombre(), "nombre", 255, true);
        String correo = v.texto(c.correo(), "correo", 255, true);
        if (correo != null) { try { correo = ValidacionCuenta.correo(correo); } catch (IllegalArgumentException e) { v.error("correo", "Correo inválido"); } }
        String contacto = v.texto(c.numContacto(), "numContacto", 25, true);
        if (contacto != null && (!contacto.matches("[+0-9() -]+") || contacto.replaceAll("[^0-9]", "").length() < 7
                || contacto.replaceAll("[^0-9]", "").length() > 15)) { v.error("numContacto", "Se requieren entre 7 y 15 dígitos"); }
        String categoria = v.texto(c.categoria(), "categoria", 100, true);
        if (c.modalidadVirtual() == null) { v.error("modalidadVirtual", "Indica si el negocio es virtual"); }
        boolean virtual = Boolean.TRUE.equals(c.modalidadVirtual());
        String direccion = v.texto(c.direccion(), "direccion", 500, !virtual);
        String foto = v.url(c.fotoPrincipal(), "fotoPrincipal");
        var galeria = new ArrayList<Multimedia>();
        if (c.galeria() != null) {
            if (c.galeria().size() > 20) { v.error("galeria", "Máximo 20 elementos"); }
            for (int i = 0; i < Math.min(c.galeria().size(), 20); i++) {
                var item = c.galeria().get(i);
                if (item == null) { v.error("galeria[" + i + "]", "Elemento obligatorio"); continue; }
                String url = v.url(item.url(), "galeria[" + i + "].url");
                if (url == null) { v.error("galeria[" + i + "].url", "URL obligatoria"); }
                String tipo = item.tipo() == null ? "" : item.tipo().strip().toUpperCase(Locale.ROOT);
                if (!Set.of("IMAGEN", "VIDEO").contains(tipo)) { v.error("galeria[" + i + "].tipo", "Usa IMAGEN o VIDEO"); }
                galeria.add(new Multimedia(url, tipo, i));
            }
        }
        v.terminar();
        return negocios.guardar(new Negocio(null, proveedorId, nombre, correo, contacto, direccion,
                categoria, virtual, foto, Instant.now(), List.copyOf(galeria)));
    }
    public Optional<Negocio> obtenerMiNegocio(Long proveedorId) { return negocios.obtenerPorProveedor(proveedorId); }
    public List<Negocio> listar() { return negocios.obtenerTodos(); }
}
