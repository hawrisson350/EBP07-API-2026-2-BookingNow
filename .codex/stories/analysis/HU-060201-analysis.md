# Análisis — HU-060201: Consultar mis reservas

- Fecha: 2026-11-21
- Ficha origen: [../detail-user-stories/HU-060201-Consultar-mis-reservas.md](../detail-user-stories/HU-060201-Consultar-mis-reservas.md)
- Azure: [109](https://dev.azure.com/CodeFactory-2026-2/EBP07%20-%20Plataforma%20de%20Reservas%20de%20Servicios/_workitems/edit/109)
- Estado: implementación inicial registrada; pruebas de comportamiento pendientes

## Requisitos confirmados

La historia y sus criterios se transcribieron desde el inventario recuperado y se vincularon con el work item de Azure. Ver ficha origen; no se agregan criterios.

## Comportamiento actual y diferencias

El endpoint `GET /api/reservas/mias/activas` quedó implementado con identidad del JWT y consulta JPA que obtiene servicio/negocio y ordena por fecha/hora. Filtra estados `ACTIVA` y `CONFIRMADA` desde la fecha actual en zona horaria de Colombia. La tabla aún requiere aplicar el contrato de estados actualizado de Sprint 3.

## Dudas y supuestos por confirmar

Confirmar qué rol consulta; criterios dicen usuario sin limitarlo al cliente. Definir zona horaria para “próxima”, catálogo canónico de estados (incluye Completada y No asistió), datos exactos de “lugar” y si detalles se incluyen en listado.

## Propuesta por capa hexagonal

Implementado mediante puerto de entrada `ConsultarReservasActivasUseCase`, servicio `ReservaService`, puerto de salida, adaptador JPA y DTO. La autorización limita la consulta al cliente autenticado.

## Contrato HTTP y persistencia

El contrato definitivo depende de las preguntas anteriores. Conservar la separación: controlador REST → puerto de entrada/caso de uso → dominio → puerto de salida → adaptador JPA. No acoplar dominio a Spring MVC ni JPA. Para requisitos frontend puros no se propone endpoint.

## Matriz de criterios

| Criterio | Cambio propuesto | Comprobación |
|---|---|---|
| C1 | Analizar según regla acordada | Prueba automatizada/API o revisión accesible correspondiente; aún no ejecutada |
| C2 | Analizar según regla acordada | Prueba automatizada/API o revisión accesible correspondiente; aún no ejecutada |
| C3 | Analizar según regla acordada | Prueba automatizada/API o revisión accesible correspondiente; aún no ejecutada |

## Dependencias y alcance pendiente

Backend involucrado. El estado de esta matriz es análisis; no representa pruebas realizadas ni funcionalidad disponible.
