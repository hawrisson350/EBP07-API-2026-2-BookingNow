package co.edu.udea.bookingnow.application.port.in.recurso;

import co.edu.udea.bookingnow.domain.model.Recurso;

public interface RegistrarRecursoUseCase { Recurso registrar(Long idProveedor, Long idNegocio, RegistrarRecursoCommand command); }
