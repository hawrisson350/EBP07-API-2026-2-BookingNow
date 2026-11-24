# Implementación — HU-060301 Recibir confirmación de reserva

- Azure PBI 35, Sprint 3.
- Al registrar una reserva, el servicio la crea en estado `CONFIRMADA` y publica un evento de email para el correo guardado en la cuenta del cliente.
- El evento se procesa después del commit mediante `PublicarEmailPort`, `EmailEventListener` y `EnviarEmailPort`.
- Resend toma el remitente de `RESEND_FROM` y la clave de `RESEND_API_KEY`, ambas fuera del código. En EC2 se verificó el remitente de prueba `BookingNow <onboarding@resend.dev>`.
- La prueba de integración verifica que se publique la confirmación al correo dinámico del cliente; el adaptador está simulado en la suite.
- El equipo confirmó que la clave configurada funciona y que Resend entrega desde `onboarding@resend.dev` al Gmail autorizado. Esta es evidencia manual reportada por el equipo, separada de la prueba automatizada.
- Estado: flujo implementado. La entrega a cualquier destinatario requiere un dominio de remitente verificado; el remitente de prueba de Resend está limitado a destinatarios permitidos por esa cuenta.
