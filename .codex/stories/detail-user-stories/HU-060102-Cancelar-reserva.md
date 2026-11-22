# HU-060102 — Cancelar reserva

**Sprint:** 3  
**ID de Azure DevOps:** [33](https://dev.azure.com/CodeFactory-2026-2/EBP07%20-%20Plataforma%20de%20Reservas%20de%20Servicios/_workitems/edit/33)  
**Fuente:** Inventario recuperado del chat «Inventario HU Sprint 3»; asociado al elemento de Azure.

## Historia

Como usuario, quiero cancelar mi reserva, para liberar el cupo reservado

## Criterios de aceptación registrados en Azure

- Cancelación exitosa. Dado que el usuario tiene una reserva en estado "Activa", cuando hace clic en el botón de cancelar y confirma la acción, entonces el sistema cambia el estado de la reserva a "Cancelada", libera el cupo en el sistema de disponibilidad, y muestra un mensaje de éxito.

- Confirmación de acción. Dado que el usuario presiona "Cancelar", cuando el sistema procesa la solicitud, entonces debe mostrar un modal o advertencia preguntando "¿Estás seguro de que deseas cancelar esta reserva?" antes de ejecutar la acción.

- Límite de tiempo. Dado que existe una política de cancelación (ej. máximo 12 o 24 horas antes), cuando el usuario intenta cancelar una reserva fuera de este plazo permitido, entonces el sistema bloquea la acción y muestra un mensaje de error explicando la política.

- Cancelación por parte del proveedor. Dado que el usuario tiene una reserva confirmada, cuando el proveedor del servicio cancela la reserva desde su panel de administración (por imprevistos o falta de disponibilidad), entonces el sistema cambia el estado de la reserva a "Cancelada por el proveedor", libera el cupo, y envía inmediatamente una notificación al usuario explicando el motivo de la cancelación.

## Observaciones de análisis

Los criterios anteriores se conservan como fueron proporcionados por Azure DevOps. Las decisiones que no estén especificadas se registran como preguntas en el análisis asociado; esta ficha no agrega reglas nuevas.
