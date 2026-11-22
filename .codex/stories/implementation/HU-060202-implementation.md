# Implementación — HU-060202 Consultar historial de reservas

- Alcance: consulta backend del historial propio, Sprint 3; Azure PBI 31.
- Implementado: endpoint `GET /api/reservas/mias/historial`; obtiene el ID del cliente autenticado y filtra estados `COMPLETADA`, `CANCELADA` y `NO_ASISTIO`; ordena por fecha y hora descendente. La respuesta incluye ID de reserva, fecha, hora, nombre del servicio, nombre del negocio y estado. Lista vacía se representa como `[]`.
- Arquitectura: puerto de entrada `ConsultarHistorialReservasUseCase`; servicio `ReservaService`; dominio de lectura `ReservaConsulta`; puerto de salida `ReservaRepositoryPort`; consulta Spring Data y adaptador JPA; DTO REST `ReservaConsultaResponse`.
- Seguridad: protegido para identidad `cliente:*`; el ID no se acepta del solicitante.
- Contrato: `GET /api/reservas/mias/historial` (Bearer JWT de cliente).
- Evidencia de compilación: `mvnw -DskipTests compile` terminó en `BUILD SUCCESS` el 2026-11-22. No se ejecutaron pruebas automatizadas en esta etapa.
- Pendiente: pruebas de comportamiento/autorización, validar estados almacenados en PostgreSQL (en especial `NO_ASISTIO`) y confirmar con frontend la presentación de la lista vacía.
- Estado: implementación inicial; aún no verificada por pruebas.
