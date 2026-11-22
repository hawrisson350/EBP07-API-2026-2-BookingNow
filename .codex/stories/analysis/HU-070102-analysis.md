# Análisis — HU-070102: Exportar reportes en PDF

- Fecha: 2026-11-21
- Ficha origen: [../detail-user-stories/HU-070102-Exportar-reportes-en-PDF.md](../detail-user-stories/HU-070102-Exportar-reportes-en-PDF.md)
- Azure: [94](https://dev.azure.com/CodeFactory-2026-2/EBP07%20-%20Plataforma%20de%20Reservas%20de%20Servicios/_workitems/edit/94)
- Estado: generación de PDF/XLSX inicial implementada; pruebas pendientes

## Requisitos confirmados

La historia y sus criterios se transcribieron desde el inventario recuperado y se vincularon con el work item de Azure. Ver ficha origen; no se agregan criterios.

## Comportamiento actual y diferencias

El endpoint `POST /api/reportes` recibe filtros, tipo y formato `PDF` o `EXCEL`; el backend genera PDF/XLSX en memoria y responde como archivo adjunto con tipo MIME apropiado. El contenido incluye tipo de reporte y periodo.

## Dudas y supuestos por confirmar

El equipo decidió que el backend genera y entrega el archivo, sin guardarlo.

## Propuesta por capa hexagonal

Implementado como puerto de salida `GeneradorReportePort` con adaptador PDFBox/Apache POI. El endpoint responde `Content-Disposition: attachment`; no se persiste archivo. Depende de los filtros/permisos de HU-070101.

## Contrato HTTP y persistencia

El contrato definitivo depende de las preguntas anteriores. Conservar la separación: controlador REST → puerto de entrada/caso de uso → dominio → puerto de salida → adaptador JPA. No acoplar dominio a Spring MVC ni JPA. Para requisitos frontend puros no se propone endpoint.

## Matriz de criterios

| Criterio | Cambio propuesto | Comprobación |
|---|---|---|
| C1 | Sin cambio backend identificado | Prueba automatizada/API o revisión accesible correspondiente; aún no ejecutada |
| C2 | Sin cambio backend identificado | Prueba automatizada/API o revisión accesible correspondiente; aún no ejecutada |
| C3 | Sin cambio backend identificado | Prueba automatizada/API o revisión accesible correspondiente; aún no ejecutada |
| C4 | Sin cambio backend identificado | Prueba automatizada/API o revisión accesible correspondiente; aún no ejecutada |

## Dependencias y alcance pendiente

La HU no requiere cambio backend evidente. El estado de esta matriz es análisis; no representa pruebas realizadas ni funcionalidad disponible.
