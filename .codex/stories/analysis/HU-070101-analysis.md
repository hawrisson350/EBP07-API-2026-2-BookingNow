# Análisis — HU-070101: Generar reportes mediante filtros

- Fecha: 2026-11-21
- Ficha origen: [../detail-user-stories/HU-070101-Generar-reportes-mediante-filtros.md](../detail-user-stories/HU-070101-Generar-reportes-mediante-filtros.md)
- Azure: [87](https://dev.azure.com/CodeFactory-2026-2/EBP07%20-%20Plataforma%20de%20Reservas%20de%20Servicios/_workitems/edit/87)
- Estado: endpoint inicial implementado; pruebas pendientes

## Requisitos confirmados

La historia y sus criterios se transcribieron desde el inventario recuperado y se vincularon con el work item de Azure. Ver ficha origen; no se agregan criterios.

## Comportamiento actual y diferencias

Existe `POST /api/reportes`, con filtros de fechas, negocio, servicio, recurso y estado, tipo `RESERVAS` o `USO_SERVICIOS_RECURSOS`, y formato PDF/EXCEL. Proveedor solo consulta sus negocios; administrador consulta globalmente. El reporte de uso agrupa servicio/recurso y cuenta reservas. El resultado se entrega como adjunto en memoria, sin archivo persistido.

## Dudas y supuestos por confirmar

El equipo acordó filtros y generación de archivo. Confirmar si el conteo de reservas por servicio/recurso satisface la métrica de consumo y validar límite máximo de fechas con el front. Falta probar con PostgreSQL.

## Propuesta por capa hexagonal

Implementado `GenerarReporteUseCase`, puerto de consulta y puerto generador de artefacto; adaptador JPA aplica filtros y pertenencia del proveedor; adaptador de salida genera PDF/XLSX con Apache PDFBox/POI.

## Contrato HTTP y persistencia

El contrato definitivo depende de las preguntas anteriores. Conservar la separación: controlador REST → puerto de entrada/caso de uso → dominio → puerto de salida → adaptador JPA. No acoplar dominio a Spring MVC ni JPA. Para requisitos frontend puros no se propone endpoint.

## Matriz de criterios

| Criterio | Cambio propuesto | Comprobación |
|---|---|---|
| C1 | Analizar según regla acordada | Prueba automatizada/API o revisión accesible correspondiente; aún no ejecutada |
| C2 | Analizar según regla acordada | Prueba automatizada/API o revisión accesible correspondiente; aún no ejecutada |
| C3 | Analizar según regla acordada | Prueba automatizada/API o revisión accesible correspondiente; aún no ejecutada |
| C4 | Analizar según regla acordada | Prueba automatizada/API o revisión accesible correspondiente; aún no ejecutada |
| C5 | Analizar según regla acordada | Prueba automatizada/API o revisión accesible correspondiente; aún no ejecutada |
| C6 | Analizar según regla acordada | Prueba automatizada/API o revisión accesible correspondiente; aún no ejecutada |
| C7 | Analizar según regla acordada | Prueba automatizada/API o revisión accesible correspondiente; aún no ejecutada |
| C8 | Analizar según regla acordada | Prueba automatizada/API o revisión accesible correspondiente; aún no ejecutada |
| C9 | Analizar según regla acordada | Prueba automatizada/API o revisión accesible correspondiente; aún no ejecutada |
| C10 | Analizar según regla acordada | Prueba automatizada/API o revisión accesible correspondiente; aún no ejecutada |
| C11 | Analizar según regla acordada | Prueba automatizada/API o revisión accesible correspondiente; aún no ejecutada |

## Dependencias y alcance pendiente

Backend involucrado. El estado de esta matriz es análisis; no representa pruebas realizadas ni funcionalidad disponible.
