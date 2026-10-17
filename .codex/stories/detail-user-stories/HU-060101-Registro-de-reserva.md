# HU-060101 — Registrar reserva con fecha y hora

**Fuente:** Azure DevOps, PBI 27, Sprint 2.

## Historia

Como usuario, quiero registrar una reserva seleccionando un servicio, una fecha y una hora disponibles, para asegurar mi cupo y asociar la reserva con mis datos como cliente.

## Criterios de aceptación

- El usuario puede seleccionar el servicio que desea reservar e iniciar el proceso desde una ventana o formulario.
- El sistema identifica si el servicio requiere empleado, recurso o ambos.
- Si requiere empleado, muestra los empleados asociados y disponibles; si requiere recurso, muestra los recursos asociados y disponibles.
- Si requiere ambos, valida la disponibilidad de ambos antes de permitir fecha y hora.
- El usuario puede elegir empleado y/o recurso disponibles según corresponda.
- El sistema consulta disponibilidad antes de mostrar fechas y horas; las horas respetan duración del servicio y horario de atención del negocio.
- La reserva queda asociada al cliente autenticado.
- Antes de guardar, se muestra el resumen seleccionado: servicio, empleado, recurso, fecha y hora.
- Al confirmar, guarda servicio, cliente, fecha, hora y los empleados o recursos elegidos.
- El horario registrado deja de estar disponible cuando corresponda.
- Si algún elemento deja de estar disponible durante el proceso, informa el caso y exige otra selección.

## Seguridad indicada en la HU

La confirmación exige autenticación. El correo y nombre se toman de la cuenta autenticada, sin permitir un campo libre para esos datos. La HU también solicita una notificación de confirmación por correo.
