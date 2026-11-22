# HU-060303 — Recibir notificación de cancelación

**Sprint:** 3  
**ID de Azure DevOps:** [37](https://dev.azure.com/CodeFactory-2026-2/EBP07%20-%20Plataforma%20de%20Reservas%20de%20Servicios/_workitems/edit/37)  
**Fuente:** Inventario recuperado del chat «Inventario HU Sprint 3»; asociado al elemento de Azure.

## Historia

Como usuario, quiero recibir una notificación cuando una reserva mía sea cancelada, para estar informado del cambio de estado y no asistir innecesariamente.

## Criterios de aceptación registrados en Azure

- Dado que el usuario cancela su propia reserva de forma exitosa, cuando el sistema actualiza el estado, entonces envía de inmediato un mensaje confirmando que "Tu reserva ha sido cancelada correctamente".

- Dado que la reserva fue cancelada por un agente externo (administrador/sistema), entonces la notificación debe incluir el motivo de la cancelación y una disculpa o instrucción para reprogramar.

## Observaciones de análisis

Los criterios anteriores se conservan como fueron proporcionados por Azure DevOps. Las decisiones que no estén especificadas se registran como preguntas en el análisis asociado; esta ficha no agrega reglas nuevas.
