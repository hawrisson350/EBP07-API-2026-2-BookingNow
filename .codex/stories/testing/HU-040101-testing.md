# Verificación — HU-040101 Registrar recurso

- Estado: **criterios de API cubiertos en H2**.
- Prueba ejecutada: `registraRecursosEmpleadosYHorarioConPermisosYValidaciones` dentro de `.\mvnw.cmd verify`; suite aprobada.
- Evidencia: registro/listado, cantidad cero, estado inválido, dueño/no dueño y falta de token.
- Persistencia PostgreSQL aún sin validar; el contrato SQL está en `docs/db/sprint-2-schema.sql`.
