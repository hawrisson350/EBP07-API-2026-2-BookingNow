# Verificación — HU-060101 Registrar reserva con fecha y hora

- Estado: **criterios centrales de API cubiertos en H2; PostgreSQL pendiente**.
- Prueba ejecutada: `consultaDisponibilidadYRegistraReservaSinDobleAsignacion` dentro de `.\mvnw.cmd verify`; suite aprobada.
- Evidencia: consulta de disponibilidad, JWT de cliente obligatorio, asociación al cliente autenticado, requerir recurso, reserva dentro/fuera del horario y rechazo de solapamiento.
- Pendientes: integrar proveedor de correo y verificar bloqueo/transacción/concurrencia con PostgreSQL real. Resumen previo y selección visual dependen del frontend.
