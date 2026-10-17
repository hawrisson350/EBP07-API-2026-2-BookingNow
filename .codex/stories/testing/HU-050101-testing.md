# Verificación — HU-050101 Registrar empleado

- Estado: **criterios de API cubiertos parcialmente en H2**.
- Prueba ejecutada: `registraRecursosEmpleadosYHorarioConPermisosYValidaciones` dentro de `.\mvnw.cmd verify`; suite aprobada.
- Evidencia: registro/listado, documento duplicado e inválido, correo inválido, titularidad por proveedor.
- No cubierto en esta prueba: formato de teléfono inválido; límite máximo de empleados sigue sin definición.
- Pendiente de definición: límite máximo de empleados por negocio; la HU no fija el número. El formato de documento/teléfono aplicado es un supuesto técnico documentado en implementación.
