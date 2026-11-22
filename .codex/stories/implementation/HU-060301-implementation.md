# Implementación — HU-060301 Recibir confirmación de reserva

- Azure PBI 35, Sprint 3.
- Al crear reserva, la aplicación publica un email con nombre de usuario, fecha y hora. El evento se despacha tras commit.
- Arquitectura: puerto `PublicarEmailPort`; adaptador Spring de publicación de eventos; listener transaccional; puerto `EnviarEmailPort`; adaptador Resend en infraestructura.
- Configuración: `RESEND_API_KEY` y `RESEND_FROM`; `RESEND_BASE_URL` opcional. La clave se configura fuera del repositorio.
- Sin configuración, el backend registra `no_configurado`; la reserva persiste y el email no se envía.
- Verificación: compilación Maven sin pruebas ejecutadas. Envío real pendiente de configurar/verificar dominio y confirmar recepción.
- Estado: integración inicial preparada; entrega del correo no verificada.
