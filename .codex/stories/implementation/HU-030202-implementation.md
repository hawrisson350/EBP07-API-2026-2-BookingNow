# Implementación — HU-030202 Asignar empleados y recursos a un servicio

- Alcance: API backend, Sprint 2.
- Implementado: reemplazar la lista de asignaciones, definir si se requieren empleados/recursos, rechazar IDs repetidos y validar que los empleados y recursos disponibles pertenezcan al mismo negocio.
- Ruta: `PUT /api/negocios/{idNegocio}/servicios/{idServicio}/asignaciones`.
- Evidencia de código: `AsignacionServicioService`, `AsignarPersonalYRecursosUseCase`, `AsignacionServicioRepositoryPort`, `AsignacionServicioRepositoryAdapter`, `AsignacionServicioController` y relaciones en `ServicioJpaEntity`.
- La respuesta de listados de servicio incluye las banderas y los IDs asociados; enviar listas vacías permite quitar asignaciones.
- Persistencia: tablas `servicio_empleados` y `servicio_recursos` en `docs/db/sprint-2-schema.sql`; no se ha aplicado en Supabase.
- Estado: asignar/listar/quitar, validaciones, pertenencia y permisos verificados en H2. Validación de relaciones con PostgreSQL pendiente.

## Criterios

| Criterio | Estado backend |
|---|---|
| Seleccionar servicio propio del negocio | Implementado con comprobación de titularidad |
| Asignar empleados/recursos disponibles del mismo negocio | Implementado |
| Definir qué recursos humanos/materiales requiere | Implementado |
| Agregar y quitar relaciones | Implementado mediante reemplazo de listas |
| Evitar duplicados | Implementado |
| Devolver asignaciones al consultar el servicio | Implementado en respuestas de servicios |
| Controles visuales de selección | Pendiente del frontend |
