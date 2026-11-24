# Implementación — HU-070101 Generar reportes mediante filtros

- Azure PBI 87, Sprint 3.
- Endpoint: `POST /api/reportes`; roles proveedor y administrador.
- Filtros: tipo de reporte, rango de fechas opcional (ambas fechas o ninguna), negocio, servicio, recurso, estado y formato.
- Proveedores quedan restringidos a sus negocios aun si envían un `idNegocio`; administradores pueden consultar globalmente.
- Tipos: `RESERVAS` lista detalle; `USO_SERVICIOS_RECURSOS` agrupa y cuenta reservas por servicio/recurso.
- Sin resultados se genera archivo con aviso en lugar de error. Archivo entregado como adjunto y solo en memoria.
- Corrección 2026-11-23: PostgreSQL rechazaba el filtro opcional de estado porque la consulta aplicaba `upper(:estado)` a un parámetro nulo (`upper(bytea)`). El adaptador ahora normaliza el estado y usa cadena vacía como “sin filtro”.
- Despliegue: versión corregida desplegada en EC2; `/health` respondió `200`.
- Verificación real en PostgreSQL/EC2: `RESERVAS` y `USO_SERVICIOS_RECURSOS` se generaron en PDF y Excel con los filtros opcionales omitidos; las cuatro respuestas devolvieron `200` y sus tipos MIME correspondientes.
- Pendiente: probar rangos de fecha, negocio, servicio, recurso y estado con distintos datos; comprobar aislamiento de proveedor y revisar el contenido interno de los archivos.
- Estado: generación básica verificada; cobertura de filtros y permisos incompleta.
