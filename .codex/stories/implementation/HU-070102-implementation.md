# Implementación — HU-070102 Exportar reportes en PDF

- Azure PBI 94, Sprint 3.
- `POST /api/reportes` acepta `formato: PDF` o `EXCEL`; el último genera `.xlsx`.
- Adaptador de salida Apache PDFBox genera PDF; Apache POI genera XLSX. Incluye tipo de reporte y periodo seleccionado. Devuelve `Content-Type` y `Content-Disposition` de descarga.
- Los bytes se generan en memoria; el backend no guarda archivos.
- Verificación: compilación Maven exitosa; no se ejecutaron pruebas ni inspección visual del PDF/XLSX.
- Estado: implementación inicial; pendiente revisión del documento generado y pruebas.
