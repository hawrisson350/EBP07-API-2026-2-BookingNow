# Sprint 2 — evidencia de verificación

- Fecha: 2026-10-17.
- Comando ejecutado: `.\mvnw.cmd verify`.
- Resultado: `BUILD SUCCESS`; 26 pruebas, 0 fallos, 0 errores y 0 omitidas (25 en `BookingNowApplicationTests`, 1 en `CloudConfigurationTests`).
- Entorno: Windows PowerShell, Java 21 configurado por Maven Wrapper.
- Las pruebas de API usan Spring Boot con servidor HTTP local y H2 (`create-drop`). No se conectaron a Supabase/PostgreSQL.
- Pruebas nuevas de Sprint 2: `buscaYConsultaDetalleDeServicioConTipoEGaleria`, `registraRecursosEmpleadosYHorarioConPermisosYValidaciones`, `asignaEmpleadosYRecursosDelNegocioAlServicioYPermiteReemplazo` y `consultaDisponibilidadYRegistraReservaSinDobleAsignacion`.
- Base de datos: el esquema de Sprint 2 no se ha aplicado en Supabase; compatibilidad real PostgreSQL y concurrencia en PostgreSQL quedan pendientes.
- La evidencia verifica comportamiento backend cubierto por los casos anteriores; no verifica interfaz frontend, correo, integración con la base compartida ni la totalidad de condiciones de producción.

## Próxima verificación necesaria

Aplicar el contrato SQL en una base PostgreSQL de prueba y repetir los flujos; verificar concurrencia real de reservas y revisar el flujo de extremo a extremo del frontend. No se deben reportar esos puntos como ejecutados hasta tener resultados reales.
