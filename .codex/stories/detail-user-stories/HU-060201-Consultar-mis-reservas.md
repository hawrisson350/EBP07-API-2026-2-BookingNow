# HU-060201 — Consultar mis reservas

**Sprint:** 3  
**ID de Azure DevOps:** [109](https://dev.azure.com/CodeFactory-2026-2/EBP07%20-%20Plataforma%20de%20Reservas%20de%20Servicios/_workitems/edit/109)  
**Fuente:** Inventario recuperado del chat «Inventario HU Sprint 3»; asociado al elemento de Azure.

## Historia

Como usuario, quiero consultar mis reservas activas, para acceder a su información

## Criterios de aceptación registrados en Azure

- Listado de activas: Dado que el usuario está autenticado, cuando accede a la sección de "Mis reservas", entonces el sistema muestra únicamente las reservas que están en estado "Activa" o "Confirmada".

- Orden y acceso a acciones: Dado que el usuario visualiza sus reservas activas, entonces estas deben ordenarse de la más próxima a la más lejana en el tiempo, y cada una debe incluir un botón/enlace para "Cancelar" o "Ver detalles".

- Sin reservas activas: Dado que el usuario no tiene ninguna reserva a futuro, cuando accede a esta vista, entonces se debe mostrar el mensaje "No tienes reservas activas en este momento" y un botón sugerido para "Crear nueva reserva".

## Observaciones de análisis

Los criterios anteriores se conservan como fueron proporcionados por Azure DevOps. Las decisiones que no estén especificadas se registran como preguntas en el análisis asociado; esta ficha no agrega reglas nuevas.
