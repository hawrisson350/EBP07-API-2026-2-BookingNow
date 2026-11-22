# Análisis — HU-060202: Consultar historial de reservas

- Fecha: 2026-11-21
- Ficha origen: [../detail-user-stories/HU-060202-Consultar-historial-de-reservas.md](../detail-user-stories/HU-060202-Consultar-historial-de-reservas.md)
- Azure: [31](https://dev.azure.com/CodeFactory-2026-2/EBP07%20-%20Plataforma%20de%20Reservas%20de%20Servicios/_workitems/edit/31)
- Estado: implementación inicial registrada; pruebas de comportamiento pendientes

## Requisitos confirmados

La historia y sus criterios se transcribieron desde el inventario recuperado y se vincularon con el work item de Azure. Ver ficha origen; no se agregan criterios.

## Comportamiento actual y diferencias

El endpoint `GET /api/reservas/mias/historial` quedó implementado con identidad del JWT, detalle de servicio/negocio y orden descendente. Filtra `COMPLETADA`, `CANCELADA`, `CANCELADA_POR_EL_PROVEEDOR` y `NO_ASISTIO`. El esquema previo solo permitía `PENDIENTE_CONFIRMACION`, `CONFIRMADA` y `CANCELADA`; `docs/db/sprint-2-schema.sql` ahora contiene un contrato local ampliado que el repositorio de BD debe revisar y aplicar.

## Dudas y supuestos por confirmar

Confirmar qué rol consulta; criterios dicen usuario sin limitarlo al cliente. Definir zona horaria para “próxima”, catálogo canónico de estados (incluye Completada y No asistió), datos exactos de “lugar” y si detalles se incluyen en listado.

## Propuesta por capa hexagonal

Implementado mediante puerto de entrada `ConsultarHistorialReservasUseCase`, servicio `ReservaService`, puerto de salida, adaptador JPA y DTO. La autorización limita la consulta al cliente autenticado.

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
