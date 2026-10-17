# Verificación — HU-030202 Asignar empleados y recursos a un servicio

- Estado: **criterios backend cubiertos en H2**.
- Prueba ejecutada: `asignaEmpleadosYRecursosDelNegocioAlServicioYPermiteReemplazo` dentro de `.\mvnw.cmd verify`; suite aprobada.
- Evidencia: asignar/listar/quitar, IDs duplicados, empleado de otro negocio, recurso no disponible y restricciones de autenticación/titularidad.
- Persistencia PostgreSQL y relaciones ORM aún sin validar contra la base compartida.
