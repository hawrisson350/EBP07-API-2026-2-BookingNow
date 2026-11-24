# Pruebas — HU-060303 Notificación de cancelación

- Fecha: 2026-11-24.
- Implementación: [HU-060303](../implementation/HU-060303-implementation.md), Azure PBI 37.
- En `mvnw verify`, `cancelarReservaNotificaAlCorreoDelClienteAsociado` recorre el endpoint HTTP como cliente propietario y verifica estado cancelado, asunto y destinatario extraído de la reserva.
- El adaptador de email está simulado: esta prueba no manda correo real.
- El equipo confirmó una prueba manual exitosa de Resend con el remitente de prueba y el Gmail permitido.

## Pendiente

- Ejecutar la cancelación de proveedor y administrador para verificar motivo/cuerpo específico.
- Comprobar en Resend la entrega de ambas variantes desde el backend desplegado.
- Confirmar que una transacción revertida no envía notificación.
