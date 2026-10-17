# Sprint 2 — implementación del backend

Fecha de registro: 2026-10-17. Estimaciones y alcance iniciales: [análisis del Sprint 2](../analysis/SPRINT-2-estimacion-y-alcance.md).

| Historia | Evidencia de implementación |
|---|---|
| HU-020202 — Configurar horario de atención | [Detalle](HU-020202-implementation.md) |
| HU-030101 — Listar información de servicio (PBI 62) | [Detalle](HU-030101-Sprint-2-implementation.md) |
| HU-030102 — Buscar servicio por nombre y tipo | [Detalle](HU-030102-implementation.md) |
| HU-030202 — Asignar empleados y recursos a servicio | [Detalle](HU-030202-implementation.md) |
| HU-040101 — Registrar recurso | [Detalle](HU-040101-implementation.md) |
| HU-050101 — Registrar empleado | [Detalle](HU-050101-implementation.md) |
| HU-060101 — Registrar reserva | [Detalle](HU-060101-implementation.md) |

## Decisiones y pendientes visibles

- Se mantuvo la arquitectura hexagonal y se agruparon los puertos de entrada por entidad/capacidad.
- El SQL de [Sprint 2](../../../docs/db/sprint-2-schema.sql) es un contrato local para revisión y aplicación por el equipo de base de datos; no se ejecutó sobre Supabase.
- La notificación por correo solicitada para reservas requiere elegir y configurar un proveedor.
- `tipoServicio` es texto libre porque Azure no define categorías controladas.
- La programación del frontend, el despliegue y la validación integrada con la base remota no están incluidos.
- HU-030101 aparece duplicada en Azure entre Sprint 1 y Sprint 2; se distinguen por título/PBI.
- Las pruebas H2 ejecutadas y su relación con los criterios están en [testing Sprint 2](../testing/SPRINT-2.md). Los resultados no comprueban PostgreSQL/Supabase, correo ni experiencia completa del frontend.
