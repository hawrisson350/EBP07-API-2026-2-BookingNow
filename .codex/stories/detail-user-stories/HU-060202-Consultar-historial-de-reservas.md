# HU-060202 — Consultar historial de reservas

**Sprint:** 3  
**ID de Azure DevOps:** [31](https://dev.azure.com/CodeFactory-2026-2/EBP07%20-%20Plataforma%20de%20Reservas%20de%20Servicios/_workitems/edit/31)  
**Fuente:** Inventario recuperado del chat «Inventario HU Sprint 3»; asociado al elemento de Azure.

## Historia

Como usuario, quiero ver mi historial de reservas, para acceder a su información

## Criterios de aceptación registrados en Azure

- Listado de historial: Dado que el usuario está autenticado, cuando accede a la sección de "Historial de reservas", entonces el sistema muestra una lista de todas sus reservas pasadas (estados: "Completada", "Cancelada" o "No asistió").

- Ordenamiento: Dado que el usuario visualiza su historial, entonces la lista debe estar ordenada cronológicamente de manera descendente (las más recientes primero).

- Información mostrada: Dado que el usuario ve un registro en el historial, entonces la tarjeta o fila debe mostrar: ID/Número de reserva, fecha, hora, servicio/lugar y el estado final de la reserva.

- Historial vacío: Dado que el usuario no ha realizado ninguna reserva en el pasado, cuando entra al historial, entonces el sistema debe mostrar un mensaje como "Aún no tienes reservas en tu historial".

## Observaciones de análisis

Los criterios anteriores se conservan como fueron proporcionados por Azure DevOps. Las decisiones que no estén especificadas se registran como preguntas en el análisis asociado; esta ficha no agrega reglas nuevas.
