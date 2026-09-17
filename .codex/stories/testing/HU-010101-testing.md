# Pruebas — HU-010101

- Fecha: 2026-09-17
- Implementación: [HU-010101](../implementation/HU-010101-implementation.md)
- Entorno: Java 21, Maven Wrapper, Spring Boot 4.1.1, HTTP local y H2.
- Estado: verificaciones de API aprobadas; pendientes explícitos por criterio.

## Resultados por criterio

| Criterio | Prueba | Resultado real |
|---|---|---|
| CA-01 | `registroLoginCorreoYTitularidad` | API verificada; presentación del mensaje pendiente |
| CA-02 | `validaRegistroYComplejidad` | 400 verificado |
| CA-03 | `validaRegistroYComplejidad` | 400 verificado |
| CA-04 | `validaRegistroYComplejidad` | 400 verificado |
| CA-05 | `correoUnicoEntreAmbosRoles; concurrenciaCorreoYNegocioMantienenUnicidad` | 409 verificado |

## Comandos, evidencia y limitaciones

`.\mvnw.cmd -B -ntp verify`: 18 pruebas aprobadas en la ejecución conjunta.
Métodos en [BookingNowApplicationTests.java](../../../src/test/java/co/edu/udea/bookingnow/BookingNowApplicationTests.java).
El reporte [SPRINT-1-testing.md](SPRINT-1-testing.md) conserva ubicación, resultado,
fallo corregido y límites de la verificación. No se ejecutaron frontend, Render,
Supabase ni PostgreSQL real. Los mensajes HTTP no acreditan su presentación visual.
