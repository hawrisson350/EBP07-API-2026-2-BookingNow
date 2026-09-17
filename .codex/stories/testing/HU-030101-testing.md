# Pruebas — HU-030101

- Fecha: 2026-09-17
- Implementación: [HU-030101](../implementation/HU-030101-implementation.md)
- Entorno: Java 21, Maven Wrapper, Spring Boot 4.1.1, HTTP local y H2.
- Estado: verificaciones de API aprobadas; pendientes explícitos por criterio.

## Resultados por criterio

| Criterio | Prueba | Resultado real |
|---|---|---|
| CA-01 | `servicioValidaNumerosCamposYNegocioPropio` | 403 para ajeno, 404 inexistente; selección visual pendiente |
| CA-02 | `servicioSeAsociaYListaConPrecioCero; servicioValidaNumerosCamposYNegocioPropio` | Registro y campos obligatorios verificados |
| CA-03 | `servicioSeAsociaYListaConPrecioCero; servicioValidaNumerosCamposYNegocioPropio` | URL HTTPS guardada; URL inválida rechazada |
| CA-04 | `servicioValidaNumerosCamposYNegocioPropio` | Cero, negativo y fracción rechazados |
| CA-05 | `servicioSeAsociaYListaConPrecioCero; servicioValidaNumerosCamposYNegocioPropio` | Precio cero aceptado; negativo rechazado |
| CA-06 | `servicioValidaNumerosCamposYNegocioPropio` | 400 y ausencia de registros inválidos verificados |
| CA-07 | `servicioSeAsociaYListaConPrecioCero; servicioValidaNumerosCamposYNegocioPropio` | Asociación y titularidad verificadas |
| CA-08 | `servicioSeAsociaYListaConPrecioCero` | Listado API y ACTIVO verificados; reservas aún fuera de alcance |

## Comandos, evidencia y limitaciones

`.\mvnw.cmd -B -ntp verify`: 18 pruebas aprobadas en la ejecución conjunta.
Métodos en [BookingNowApplicationTests.java](../../../src/test/java/co/edu/udea/bookingnow/BookingNowApplicationTests.java).
El reporte [SPRINT-1-testing.md](SPRINT-1-testing.md) conserva ubicación, resultado,
fallo corregido y límites de la verificación. No se ejecutaron frontend, Render,
Supabase ni PostgreSQL real. Los mensajes HTTP no acreditan su presentación visual.
