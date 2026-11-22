# Implementación — HU-060201 Consultar mis reservas

- Alcance: consulta backend de reservas propias, Sprint 3; Azure PBI 109.
- Implementado: endpoint `GET /api/reservas/mias/activas`; obtiene el ID de cliente de la identidad JWT, filtra desde la hora actual de Colombia y considera estados `ACTIVA` o `CONFIRMADA`; ordena ascendente por fecha y hora. La respuesta incluye fecha, hora, nombre del servicio, nombre del negocio y estado. Lista vacía se representa como `[]`.
- Arquitectura: puerto de entrada `ConsultarReservasActivasUseCase`; servicio `ReservaService`; dominio de lectura `ReservaConsulta`; puerto de salida `ReservaRepositoryPort`; consulta Spring Data y adaptador JPA; DTO REST `ReservaConsultaResponse`.
- Seguridad: protegido para identidad `cliente:*`; el cliente no se selecciona desde la solicitud, por lo que no se puede pedir el listado de otra cuenta con un ID arbitrario.
- Contrato: `GET /api/reservas/mias/activas` (Bearer JWT de cliente).
- Evidencia de compilación: `mvnw -DskipTests compile` terminó en `BUILD SUCCESS` el 2026-11-22. No se ejecutaron pruebas automatizadas en esta etapa.
- Pendiente: pruebas de comportamiento/autorización, validar datos reales de PostgreSQL y confirmar con frontend el mensaje de lista vacía.
- Estado: implementación inicial; aún no verificada por pruebas.
