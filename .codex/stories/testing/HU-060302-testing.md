# Pruebas — HU-060302 Recordatorio de reserva

- Fecha: 2026-11-22
- Implementación: [HU-060302](../implementation/HU-060302-implementation.md), Azure PBI 36.
- Estado: pendiente; no se ejecutaron pruebas automatizadas ni envío real.

## Comprobaciones pendientes

- Reserva activa/confirmada dentro de 24h genera un solo email con nombre, fecha y hora.
- Reservas canceladas no generan recordatorio.
- Reserva fuera de ventana no se procesa; dentro de 24h por creación tardía se procesa en siguiente ciclo.
- Dos ciclos o dos instancias concurrentes no duplican el recordatorio.
- Resend configurado entrega correo; fallos y ausencia de configuración no detienen API.
- PostgreSQL tiene `recordatorio_procesado_at` y las consultas del scheduler funcionan.
