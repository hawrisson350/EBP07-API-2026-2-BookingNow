# Implementación — HU-040101 Registrar recurso

- Alcance: API backend, Sprint 2.
- Implementado: alta y listado de recursos por negocio; validación de nombre, descripción, cantidad positiva y estado; el proveedor solo opera sobre sus propios negocios.
- Rutas: `POST/GET /api/negocios/{idNegocio}/recursos`.
- Evidencia de código: `RecursoService`, `RecursoRepositoryPort`, `RecursoJpaEntity`, `RecursoRepositoryAdapter` y `EmpleadoRecursoController`.
- Persistencia: tabla `bookingnow.recursos` en `docs/db/sprint-2-schema.sql`; no se ha aplicado en Supabase.
- Estado: alta, listado, validaciones de cantidad/estado y permisos verificados en H2. Persistencia PostgreSQL pendiente.

## Criterios

| Criterio | Estado backend |
|---|---|
| Asociar al negocio del proveedor autenticado | Implementado |
| Capturar nombre, cantidad, estado y descripción | Implementado |
| Cantidad positiva y datos obligatorios | Implementado |
| Estados disponible, no disponible o mantenimiento | Implementado |
| Mostrar recurso en listado del negocio | Implementado |
| Respuesta visual de error y listado en pantalla | Pendiente del frontend |
