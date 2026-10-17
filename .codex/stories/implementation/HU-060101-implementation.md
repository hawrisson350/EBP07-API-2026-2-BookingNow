# Implementación — HU-060101 Registrar reserva con fecha y hora

- Alcance: API backend, Sprint 2. Azure PBI 27; el título y los criterios describen reservas aunque el identificador parezca pertenecer a otra agrupación.
- Implementado: consulta de disponibilidad por servicio/fecha; valida horario de atención, duración, asignaciones y solapamientos. El alta exige JWT de cliente y toma el ID de cliente de la identidad autenticada. En una transacción bloquea el servicio y los recursos seleccionados antes de comprobar de nuevo los conflictos.
- Rutas: `GET /api/servicios/{idServicio}/disponibilidad?fecha=AAAA-MM-DD`; `POST /api/reservas`.
- Evidencia de código: `ReservaService`, casos de uso y modelos de reserva, `ReservaRepositoryPort`, adaptadores JPA y `ReservaController`.
- Persistencia: tabla `bookingnow.reservas` en `docs/db/sprint-2-schema.sql`; no se ha aplicado en Supabase.
- Pendiente: correo de confirmación, resumen visual previo, flujo de selección del frontend y comprobación concurrente en PostgreSQL/Supabase. La API registra el estado `CONFIRMADA`.
- Estado: implementación backend parcial respecto a la historia completa; verificación de comportamiento pendiente.

## Criterios

| Criterio | Estado backend |
|---|---|
| Mostrar recursos humanos/materiales asociados que estén disponibles | Disponibilidad API entrega IDs por franja |
| Validar requisitos antes de mostrar fechas/horas | Implementado mediante agenda, duración y horario configurado |
| Asociar la reserva al cliente autenticado | Implementado; cliente no viene como dato libre |
| Evitar doble reserva y detectar cambios de disponibilidad | Revalidación transaccional y bloqueos implementados; requiere verificación PostgreSQL |
| Guardar servicio, fecha, hora y elemento elegido | Implementado |
| Resumen previo y selección visual | Pendiente del frontend |
| Notificación de confirmación por correo | Pendiente de proveedor y configuración de correo |
