# HU-060302 — Recibir recordatorio de reserva

**Sprint:** 3  
**ID de Azure DevOps:** [36](https://dev.azure.com/CodeFactory-2026-2/EBP07%20-%20Plataforma%20de%20Reservas%20de%20Servicios/_workitems/edit/36)  
**Fuente:** Inventario recuperado del chat «Inventario HU Sprint 3»; asociado al elemento de Azure.

## Historia

Como usuario, quiero recibir un recordatorio antes de la fecha de mi reserva, para evitar olvidar la reserva.

## Criterios de aceptación registrados en Azure

- Disparo programado. Dado que el usuario tiene una reserva en estado "Activa", cuando falte un tiempo predeterminado para la cita (ej. 24 horas o 2 horas antes), entonces el sistema ejecuta un proceso que le envía una notificación de recordatorio.

- Exclusión de canceladas. Dado que el proceso de recordatorios se ejecuta, cuando evalúa las reservas próximas, entonces debe omitir estrictamente cualquier reserva que haya sido marcada como "Cancelada".

- Contenido del recordatorio. Dado que el usuario recibe la notificación, entonces el mensaje debe indicar claramente que es un recordatorio, especificando la fecha y hora exacta.

## Observaciones de análisis

Los criterios anteriores se conservan como fueron proporcionados por Azure DevOps. Las decisiones que no estén especificadas se registran como preguntas en el análisis asociado; esta ficha no agrega reglas nuevas.
