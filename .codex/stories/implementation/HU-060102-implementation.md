# Implementación — HU-060102 Cancelar reserva

- Azure PBI 33, Sprint 3.
- Endpoint: `POST /api/reservas/{idReserva}/cancelacion`; acepta opcionalmente `{ "motivo": "..." }`.
- Autorización: cliente dueño de la reserva, proveedor dueño del negocio o administrador. Motivo requerido para proveedor/administrador.
- Regla implementada: solo estados `ACTIVA`/`CONFIRMADA`; se permite cancelar hasta dos horas antes (incluido exactamente el límite). El cliente cancela a `CANCELADA`; el proveedor a `CANCELADA_POR_EL_PROVEEDOR`; el administrador a `CANCELADA`.
- Persistencia transaccional con bloqueo de la reserva. Los dos estados cancelados se excluyen de disponibilidad. Email se publica para envío después del commit.
- Evidencia: puerto de entrada, servicio de aplicación, modelo de lectura para autorización, repositorio JPA y controlador REST. Contrato de estados actualizado en `docs/db/sprint-2-schema.sql`.
- Verificación: `mvnw -DskipTests compile` terminó en `BUILD SUCCESS`; no se ejecutaron pruebas de comportamiento ni PostgreSQL.
- Pendiente: diálogo de confirmación en React; aplicar contrato SQL en la base; pruebas de titularidad, roles, límite horario, estado e impacto en cupo.
- Estado: implementación inicial; no verificada por pruebas.
