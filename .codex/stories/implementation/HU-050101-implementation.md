# Implementación — HU-050101 Registrar empleado

- Alcance: API backend, Sprint 2.
- Implementado: alta y listado por negocio con autorización de titularidad, validación de campos, correo, teléfono y formato técnico de documento; no se permite repetir documento dentro del mismo negocio.
- Rutas: `POST/GET /api/negocios/{idNegocio}/empleados`.
- Evidencia de código: `EmpleadoService`, `EmpleadoRepositoryPort`, `EmpleadoJpaEntity`, `EmpleadoRepositoryAdapter` y `EmpleadoRecursoController`.
- Persistencia: tabla `bookingnow.empleados` en `docs/db/sprint-2-schema.sql`; no se ha aplicado en Supabase.
- Supuesto técnico: documento de 5–15 dígitos y teléfono de 7–15 dígitos numéricos, con símbolos telefónicos comunes. Azure no define esos rangos.
- Estado: implementación backend lista; límite máximo de empleados por negocio no especificado y no implementado; verificación de comportamiento pendiente.

## Criterios

| Criterio | Estado backend |
|---|---|
| Asociar al negocio del proveedor autenticado | Implementado |
| Nombres, documento, teléfono y correo obligatorios | Implementado |
| Validar documento y evitar duplicado dentro del negocio | Implementado con el supuesto de formato indicado |
| Validar formato de correo y campos | Implementado |
| Mostrar empleado en listado del negocio | Implementado |
| Mensajes y formularios visuales | Pendiente del frontend |
| Límite máximo de empleados | Pendiente de definición por el equipo |
