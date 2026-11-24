# Implementación — HU-070102 Exportar reportes en PDF

- Azure PBI 94, Sprint 3.
- `POST /api/reportes` acepta `formato: PDF` o `EXCEL`; el último genera `.xlsx`.
- Adaptador de salida Apache PDFBox genera PDF; Apache POI genera XLSX. Incluye tipo de reporte y periodo seleccionado. Devuelve `Content-Type` y `Content-Disposition` de descarga.
- Los bytes se generan en memoria; el backend no guarda archivos.
- Corrección 2026-11-23: se corrigió el filtro de estado nulo de la consulta PostgreSQL que impedía generar los archivos cuando el front omitía ese filtro.
- Despliegue y verificación real en EC2: `RESERVAS` y `USO_SERVICIOS_RECURSOS` respondieron `200` en PDF y XLSX, con `Content-Type` y nombre de archivo adjunto correctos. Los archivos se devolvieron en memoria; no se guardaron en el servidor.
- Pendiente: revisar visualmente el contenido con Excel/LibreOffice y un lector PDF, y validar que las filas y agrupaciones correspondan con los filtros.
- Estado: generación y descarga HTTP verificadas; inspección de contenido pendiente.
