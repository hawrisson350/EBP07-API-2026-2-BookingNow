package co.edu.udea.bookingnow.application.port.in.negocio;

import co.edu.udea.bookingnow.domain.model.Negocio;
import java.util.List;

/** Consulta global de negocios usada por el administrador. */
public interface ListarNegociosUseCase {
    List<Negocio> listar();
}
