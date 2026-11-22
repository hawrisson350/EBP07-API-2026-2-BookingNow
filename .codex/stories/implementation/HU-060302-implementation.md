# Implementación — HU-060302 Recibir recordatorio de reserva

- Azure PBI 36, Sprint 3.
- El equipo definió email 24 horas antes.
- `ProcesarRecordatoriosService` consulta reservas `ACTIVA`/`CONFIRMADA` con cita entre ahora y las siguientes 24 horas, en zona `America/Bogota`; cada cinco minutos el scheduler procesa las pendientes.
- La consulta excluye reservas canceladas. Actualiza `recordatorio_procesado_at` de forma condicional antes de publicar el email para evitar intentos duplicados.
- Si una reserva se crea con menos de 24 horas de anticipación, se procesa en la siguiente ejecución. Si Resend falla luego del registro como procesado, actualmente no se reintenta.
- El scheduler solo se activa con API key y remitente Resend presentes. La base necesita el nuevo campo del contrato `docs/db/sprint-2-schema.sql`; no se ha aplicado en PostgreSQL.
- Verificación: compilación Maven local; no se ejecutaron pruebas de comportamiento ni recepción real.
- Estado: implementación inicial; pendiente configurar Resend y verificar horario, cancelaciones y entrega.
