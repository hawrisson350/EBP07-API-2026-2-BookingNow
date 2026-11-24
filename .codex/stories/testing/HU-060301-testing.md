# Pruebas — HU-060301 Confirmación de reserva por email

- Fecha: 2026-11-24.
- Implementación: [HU-060301](../implementation/HU-060301-implementation.md), Azure PBI 35.
- En `mvnw verify`, la prueba de crear reserva verifica estado `CONFIRMADA` y que la interfaz de envío reciba el correo del cliente autenticado. El proveedor de correo está simulado en pruebas.
- En EC2 se comprobó que `RESEND_FROM` apunta al remitente de prueba de BookingNow. El equipo reportó una entrega manual exitosa con ese remitente al Gmail autorizado en Resend; no se usó la clave en esta ejecución.

## Límites de esta evidencia

- La suite no contacta Resend ni demuestra recepción del correo en el momento de esta prueba.
- `onboarding@resend.dev` no es un remitente de producción universal; para destinatarios no autorizados se necesita verificar un dominio propio en Resend.
- No se fija el correo del destinatario en el backend: procede del registro del cliente asociado a la reserva.
