# Pruebas — HU-070101 Reportes mediante filtros

- Fecha: 2026-11-23
- Implementación: [HU-070101](../implementation/HU-070101-implementation.md), Azure PBI 87.
- Estado: verificación parcial realizada contra la API desplegada y PostgreSQL de Supabase.

## Evidencia ejecutada

- Inicio de sesión de administrador: `200`.
- `POST /api/reportes` con `tipoReporte=RESERVAS`, filtros opcionales nulos y formatos `PDF` y `EXCEL`: ambas respuestas `200`.
- `POST /api/reportes` con `tipoReporte=USO_SERVICIOS_RECURSOS`, filtros opcionales nulos y formatos `PDF` y `EXCEL`: ambas respuestas `200`.
- Se confirmó que las respuestas son archivos adjuntos con los MIME PDF y XLSX esperados. No se guardó ni expuso el contenido durante la comprobación.
- El fallo inicial reproducido fue `500` de PostgreSQL: `function upper(bytea) does not exist`, causado por `upper(:estado)` cuando `estado` era nulo. Tras normalizar el parámetro en el adaptador, las cuatro combinaciones básicas respondieron correctamente.

## Comprobaciones pendientes

- Filtros de fechas, negocio, servicio, recurso y estado con datos que permitan confirmar inclusión/exclusión.
- Aislamiento de proveedor a sus negocios; acceso global para administrador.
- Resultado vacío como archivo con aviso.
- Conteos de uso por servicio/recurso y compatibilidad PostgreSQL.
