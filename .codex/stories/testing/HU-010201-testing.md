# Pruebas — HU-010201

- Fecha: 2026-09-17
- Implementación: [HU-010201](../implementation/HU-010201-implementation.md)
- Entorno: Java 21, Maven Wrapper, Spring Boot 4.1.1, HTTP local y H2.
- Estado: verificaciones de API aprobadas; pendientes explícitos por criterio.

## Resultados por criterio

| Criterio | Prueba | Resultado real |
|---|---|---|
| CA-01 | `registroLoginCorreoYTitularidad` | API verificada; pantalla pendiente |
| CA-02 | `loginNoRevelaExistenciaYValidaCampos` | 401 y texto exacto verificados |
| CA-03 | `loginNoRevelaExistenciaYValidaCampos` | 401 y texto exacto verificados |
| CA-04 | `loginNoRevelaExistenciaYValidaCampos` | 400 y mapa campos verificados |
| CA-05 | `loginNoRevelaExistenciaYValidaCampos` | 400 verificado |
| CA-06 | `registroLoginCorreoYTitularidad` | Verificado para ambos roles |
| CA-07 | `registroLoginCorreoYTitularidad; negocioValidaObligatoriosFormatosYPermisos; servicioValidaNumerosCamposYNegocioPropio` | Permisos API verificados; opciones visuales pendientes |
| CA-08 | `cuentaNoHabilitadaNoIniciaNiReutilizaToken` | 403 en login; JWT anterior rechazado con 401 |
| CA-09 | `Requiere frontend` | No ejecutado: esta API no contiene formulario |
| CA-10 | `registroLoginCorreoYTitularidad` | Local caliente: cliente 66 ms, proveedor 65 ms; pendiente despliegue/red/arranque frío |

## Comandos, evidencia y limitaciones

`.\mvnw.cmd -B -ntp verify`: 18 pruebas aprobadas en la ejecución conjunta.
Métodos en [BookingNowApplicationTests.java](../../../src/test/java/co/edu/udea/bookingnow/BookingNowApplicationTests.java).
El reporte [SPRINT-1-testing.md](SPRINT-1-testing.md) conserva ubicación, resultado,
fallo corregido y límites de la verificación. No se ejecutaron frontend, Render,
Supabase ni PostgreSQL real. Los mensajes HTTP no acreditan su presentación visual.
