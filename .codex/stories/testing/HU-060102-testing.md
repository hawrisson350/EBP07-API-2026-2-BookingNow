# Pruebas — HU-060102 Cancelar reserva

- Fecha: 2026-11-22
- Implementación: [HU-060102](../implementation/HU-060102-implementation.md), Azure PBI 33.
- Estado: pendiente; no se ejecutaron pruebas de comportamiento.

## Comprobaciones pendientes

- Permitir cancelación por cliente dueño, proveedor dueño y administrador.
- Rechazar otros usuarios/roles y reservas en otros estados.
- Verificar límite exacto de dos horas y bloqueo cuando falte menos.
- Confirmar motivo requerido al cancelar como proveedor/administrador.
- Confirmar cambio de estado, liberación del cupo y notificación posterior al commit.
- Probar con PostgreSQL el bloqueo de fila y el contrato SQL.
