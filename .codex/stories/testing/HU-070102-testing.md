# Pruebas — HU-070102 Exportar reportes PDF/Excel

- Fecha: 2026-11-23
- Implementación: [HU-070102](../implementation/HU-070102-implementation.md), Azure PBI 94.
- Estado: generación HTTP verificada en EC2; revisión visual y del contenido pendiente.

## Evidencia ejecutada

- PDF de `RESERVAS`: `200`, `application/pdf`, adjunto `bookingnow-reservas-todo-hoy.pdf`.
- Excel de `RESERVAS`: `200`, `application/vnd.openxmlformats-officedocument.spreadsheetml.sheet`, adjunto `.xlsx`.
- PDF de `USO_SERVICIOS_RECURSOS`: `200`, `application/pdf`.
- Excel de `USO_SERVICIOS_RECURSOS`: `200`, MIME XLSX.
- Los cuatro archivos tuvieron contenido no vacío. No se guardaron en EC2.

## Comprobaciones pendientes

- Respuesta PDF con tipo MIME y descarga correctos.
- Respuesta Excel `.xlsx` legible por Excel/LibreOffice.
- Título, filtros y periodo incluidos; filas/agrupaciones coinciden con resultados.
- Volumen de respuesta razonable y sin archivos persistidos en servidor.
