# Pruebas — HU-060102 Cancelar reserva

- Fecha: 2026-11-24.
- Implementación: [HU-060102](../implementation/HU-060102-implementation.md), Azure PBI 33.
- Comando: `./mvnw verify` (Windows: `mvnw.cmd verify`).
- Resultado de ejecución local: 36 pruebas; 0 fallas, 0 errores, 0 omitidas.
- Entorno: Spring Boot con H2 y adaptador de correo simulado; no prueba PostgreSQL ni envía emails durante la suite.

## Criterios comprobados

- State permite cancelar `ACTIVA`/`CONFIRMADA` y conserva `CANCELADA` o `CANCELADA_POR_EL_PROVEEDOR` según el actor.
- State rechaza `PENDIENTE_CONFIRMACION`, `COMPLETADA`, estados cancelados y `NO_ASISTIO`.
- El flujo HTTP cancela una reserva del cliente propietario y devuelve estado `CANCELADA`.
- La notificación de cancelación se publica al correo del cliente asociado a la reserva; la integración usa correo simulado.

## Pendiente

- Pruebas específicas de fecha límite exacta de dos horas y de menos de dos horas.
- Pruebas de titularidad ajena, roles de proveedor/administrador y motivo obligatorio.
- Compatibilidad y bloqueo transaccional en PostgreSQL.
- Recepción real del correo en este test. El equipo confirmó manualmente que Resend con `onboarding@resend.dev` entrega al Gmail permitido.
