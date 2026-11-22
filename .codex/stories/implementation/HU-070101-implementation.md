# Implementación — HU-070101 Generar reportes mediante filtros

- Azure PBI 87, Sprint 3.
- Endpoint: `POST /api/reportes`; roles proveedor y administrador.
- Filtros: tipo de reporte, rango de fechas opcional (ambas fechas o ninguna), negocio, servicio, recurso, estado y formato.
- Proveedores quedan restringidos a sus negocios aun si envían un `idNegocio`; administradores pueden consultar globalmente.
- Tipos: `RESERVAS` lista detalle; `USO_SERVICIOS_RECURSOS` agrupa y cuenta reservas por servicio/recurso.
- Sin resultados se genera archivo con aviso en lugar de error. Archivo entregado como adjunto y solo en memoria.
- Verificación: `mvnw -DskipTests compile` finalizó con `BUILD SUCCESS`; no se ejecutaron pruebas de filtros/permisos ni consulta PostgreSQL.
- Pendiente: validar que el conteo por servicio/recurso representa la métrica esperada por el equipo; completar pruebas.
- Estado: implementación inicial; no verificada por pruebas.
