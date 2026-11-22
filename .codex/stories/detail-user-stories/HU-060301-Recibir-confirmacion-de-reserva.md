# HU-060301 — Recibir confirmación de reserva

**Sprint:** 3  
**ID de Azure DevOps:** [35](https://dev.azure.com/CodeFactory-2026-2/EBP07%20-%20Plataforma%20de%20Reservas%20de%20Servicios/_workitems/edit/35)  
**Fuente:** Inventario recuperado del chat «Inventario HU Sprint 3»; asociado al elemento de Azure.

## Historia

Como usuario, quiero recibir un mensaje de confirmación tras realizar mi reserva, para tener la certeza de que mi cupo fue asignado exitosamente.

## Criterios de aceptación registrados en Azure

- Disparo automático. Dado que el usuario completa exitosamente el flujo de una nueva reserva, cuando el sistema guarda el registro en la base de datos, entonces envía automáticamente una notificación (email, SMS) confirmando la acción.

- Contenido del mensaje. Dado que el sistema genera el mensaje de confirmación, entonces este debe incluir obligatoriamente: Nombre del usuario, fecha y hora.

## Observaciones de análisis

Los criterios anteriores se conservan como fueron proporcionados por Azure DevOps. Las decisiones que no estén especificadas se registran como preguntas en el análisis asociado; esta ficha no agrega reglas nuevas.
