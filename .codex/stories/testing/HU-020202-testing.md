# Verificación — HU-020202 Configurar horario de atención

- Estado: **criterios backend cubiertos en H2**.
- Prueba ejecutada: `registraRecursosEmpleadosYHorarioConPermisosYValidaciones` dentro de `.\mvnw.cmd verify`; suite aprobada.
- Evidencia: horario válido, cierre anterior, días repetidos, dueño/no dueño, falta de token, reemplazo por lista vacía y consulta del resultado.
- No ejecutado: PostgreSQL/Supabase ni integración del horario dentro del formulario de creación del negocio.
- Brecha conocida: la API es un paso separado del alta del negocio.
