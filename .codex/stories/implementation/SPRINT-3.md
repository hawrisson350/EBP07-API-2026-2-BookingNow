# Sprint 3 — implementación inicial del backend

| HU / Azure | Evidencia |
|---|---|
| HU-060201 / PBI 109 | [Consultar mis reservas](HU-060201-implementation.md) |
| HU-060202 / PBI 31 | [Consultar historial](HU-060202-implementation.md) |
| HU-060102 / PBI 33 | [Cancelar reserva](HU-060102-implementation.md) |
| HU-060301 / PBI 35 | [Confirmación por email](HU-060301-implementation.md) |
| HU-060302 / PBI 36 | [Recordatorio 24 h antes](HU-060302-implementation.md) |
| HU-060303 / PBI 37 | [Notificación de cancelación](HU-060303-implementation.md) |
| HU-070101 / PBI 87 | [Reportes con filtros](HU-070101-implementation.md) |
| HU-070102 / PBI 94 | [Exportación PDF/Excel](HU-070102-implementation.md) |

## Verificación y pendientes visibles

- `mvnw verify` ejecutado el 2026-11-24: 36 pruebas aprobadas, 0 fallas, con H2. Se añadieron verificaciones de correo de confirmación/cancelación con adaptador simulado y transiciones State. No equivale a una prueba contra PostgreSQL ni a envío automático real.
- El equipo confirmó una prueba manual de Resend con `onboarding@resend.dev` al Gmail permitido. La EC2 usa el remitente configurado en `RESEND_FROM`; el destinatario se toma de la cuenta del cliente asociada a la reserva.
- HU-060302 recordatorio: el scheduler está configurado a 24 horas antes; falta una prueba controlada de ejecución y recepción. Los fallos no se reintentan actualmente.
- El contrato SQL y concurrencia todavía requieren verificarse contra el esquema y entorno de PostgreSQL/Supabase utilizados por el equipo.
- HU-A11Y-01/02 corresponden al frontend React; validar allí con el equipo. El backend no sustituye esa evidencia.
- El inventario de demo, matriz de pruebas y guía para mostrar el dashboard están en `docs/demo/guion-demo-api-logs-y-inventario.md`.
