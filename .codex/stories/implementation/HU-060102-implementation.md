# Implementación — HU-060102 Cancelar reserva

- Azure PBI 33, Sprint 3.
- Endpoint: `POST /api/reservas/{idReserva}/cancelacion`; acepta opcionalmente `{ "motivo": "..." }`.
- Autorización: cliente propietario, proveedor del negocio o administrador. El motivo es obligatorio para proveedor/administrador.
- La reserva debe estar `ACTIVA` o `CONFIRMADA` y faltar al menos dos horas para la cita. Cliente/admin transicionan a `CANCELADA`; proveedor a `CANCELADA_POR_EL_PROVEEDOR`.
- El dominio aplica State: `EstadoReserva` crea el objeto de estado desde el valor persistido; el estado cancelable decide el siguiente valor y el no cancelable rechaza transiciones posteriores. Se conservan los códigos persistidos y el contrato HTTP.
- `CancelarReservaService` mantiene autorización, titularidad, tiempo y motivo; la actualización de estado y publicación del email permanecen en la transacción, con entrega del evento después del commit.
- Archivos principales: `domain/state/`, `application/service/CancelarReservaService.java`, `infrastructure/adapter/out/persistence/ReservaRepositoryAdapter.java` y `infrastructure/adapter/in/rest/ReservaController.java`.
- Verificación: `mvnw verify`, 36 pruebas aprobadas con H2. Incluye transiciones State e integración HTTP de cancelación del propietario; el correo se simula en la prueba.
- Pendiente: integración/contrato PostgreSQL en este entorno; prueba específica de borde temporal, propietario no autorizado y motivo requerido; interfaz de confirmación en React.
