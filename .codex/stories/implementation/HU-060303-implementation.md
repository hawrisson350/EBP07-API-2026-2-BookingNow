# Implementación — HU-060303 Recibir notificación de cancelación

- Azure PBI 37, Sprint 3.
- El servicio de cancelación publica el mensaje al correo de cliente que se obtiene en la lectura de la reserva.
- El envío ocurre después del commit usando los mismos puertos/adaptadores de HU-060301. Cliente recibe confirmación; proveedor/administrador reciben contenido con motivo y orientación para reprogramar al cliente.
- `mvnw verify` prueba el flujo del cliente con adaptador de correo simulado y verifica que el destinatario es el correo asociado a la reserva. La prueba no envía email real.
- El equipo confirmó manualmente la entrega de Resend con el remitente de prueba a un destinatario autorizado.
