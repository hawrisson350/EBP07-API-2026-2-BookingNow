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

## Pendientes visibles

- HU-060302 recordatorio: programado para 24 horas antes; falta validar por pruebas y enviar con Resend configurado.
- Resend está integrado mediante `RESEND_API_KEY` y `RESEND_FROM`; falta configurar remitente y comprobar entrega real.
- Aplicar y validar el contrato SQL actualizado en PostgreSQL/Supabase; no se ejecutó remotamente.
- No se ejecutaron pruebas de comportamiento ni de integración en PostgreSQL.
- HU-A11Y-01/02 se implementan/evidencian en React; el servicio de logs se planificará posteriormente.
