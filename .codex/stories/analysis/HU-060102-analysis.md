# Análisis — HU-060102: Cancelar reserva

- Fecha: 2026-11-21
- Ficha origen: [../detail-user-stories/HU-060102-Cancelar-reserva.md](../detail-user-stories/HU-060102-Cancelar-reserva.md)
- Azure: [33](https://dev.azure.com/CodeFactory-2026-2/EBP07%20-%20Plataforma%20de%20Reservas%20de%20Servicios/_workitems/edit/33)
- Estado: implementación inicial registrada; pruebas de comportamiento pendientes

## Requisitos confirmados

La historia y sus criterios se transcribieron desde el inventario recuperado y se vincularon con el work item de Azure. Ver ficha origen; no se agregan criterios.

## Comportamiento actual y diferencias

La cancelación se implementó en `POST /api/reservas/{idReserva}/cancelacion`: el cliente dueño, proveedor dueño del negocio o administrador puede solicitarla; debe faltar al menos dos horas y solo se permiten estados ACTIVA/CONFIRMADA. Se persiste `CANCELADA` o `CANCELADA_POR_EL_PROVEEDOR`, el cual también se excluye de disponibilidad. Proveedor/administrador deben indicar motivo.

## Dudas y supuestos por confirmar

La ventana y actores ya fueron definidos por el equipo. Pendiente validar comportamiento con pruebas y base PostgreSQL; el front debe mostrar diálogo de confirmación antes de llamar a la API.

## Propuesta por capa hexagonal

Puerto de entrada `CancelarReservaUseCase`; verificación de actor en `CancelarReservaService`; `ReservaRepositoryPort` y adaptador JPA bloquean y actualizan en transacción. La notificación se publica por puerto y se envía después del commit. La base real aún requiere ejecutar el contrato SQL actualizado.

## Contrato HTTP y persistencia

El contrato definitivo depende de las preguntas anteriores. Conservar la separación: controlador REST → puerto de entrada/caso de uso → dominio → puerto de salida → adaptador JPA. No acoplar dominio a Spring MVC ni JPA. Para requisitos frontend puros no se propone endpoint.

## Matriz de criterios

| Criterio | Cambio propuesto | Comprobación |
|---|---|---|
| C1 | Analizar según regla acordada | Prueba automatizada/API o revisión accesible correspondiente; aún no ejecutada |
| C2 | Analizar según regla acordada | Prueba automatizada/API o revisión accesible correspondiente; aún no ejecutada |
| C3 | Analizar según regla acordada | Prueba automatizada/API o revisión accesible correspondiente; aún no ejecutada |
| C4 | Analizar según regla acordada | Prueba automatizada/API o revisión accesible correspondiente; aún no ejecutada |

## Dependencias y alcance pendiente

Backend involucrado. El estado de esta matriz es análisis; no representa pruebas realizadas ni funcionalidad disponible.
