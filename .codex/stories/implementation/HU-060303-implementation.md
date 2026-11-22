# Implementación — HU-060303 Recibir notificación de cancelación

- Azure PBI 37, Sprint 3.
- La cancelación de cliente genera email de confirmación; la de proveedor/administrador incluye motivo y orientación para reprogramar.
- El correo se publica después del commit mediante los puertos/adaptadores documentados en HU-060301.
- Sin configuración Resend, la reserva sí cambia de estado pero el correo no se entrega.
- Verificación: compilación Maven; no se ejecutaron pruebas de comportamiento ni envío real.
- Estado: integración inicial; pendiente configurar proveedor y probar recepción.
