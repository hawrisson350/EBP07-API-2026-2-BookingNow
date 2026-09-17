# Pruebas — HU-020101

- Fecha: 2026-09-17
- Implementación: [HU-020101](../implementation/HU-020101-implementation.md)
- Entorno: Java 21, Maven Wrapper, Spring Boot 4.1.1, HTTP local y H2.
- Estado: verificaciones de API aprobadas; pendientes explícitos por criterio.

## Resultados por criterio

| Criterio | Prueba | Resultado real |
|---|---|---|
| CA-01 | `negocioVirtualGaleriaYUnicoProveedor` | puedeRegistrar=true verificado; formulario pendiente |
| CA-02 | `negocioVirtualGaleriaYUnicoProveedor; concurrenciaCorreoYNegocioMantienenUnicidad` | puedeRegistrar=false y 409 verificados; ocultar opción pendiente |
| CA-03 | `negocioVirtualGaleriaYUnicoProveedor; negocioValidaObligatoriosFormatosYPermisos` | Registro y lectura persistida verificados |
| CA-04 | `negocioVirtualGaleriaYUnicoProveedor` | 201 y dirección null verificados |
| CA-05 | `negocioValidaObligatoriosFormatosYPermisos` | 400 y campos verificados |
| CA-06 | `negocioValidaObligatoriosFormatosYPermisos` | Correo/contacto/URL/tipo de multimedia inválidos rechazados |
| CA-07 | `negocioVirtualGaleriaYUnicoProveedor; negocioValidaObligatoriosFormatosYPermisos` | idProveedor del JWT y permisos verificados |
| CA-08 | `negocioVirtualGaleriaYUnicoProveedor` | 201 y mensaje verificados; presentación pendiente |

## Comandos, evidencia y limitaciones

`.\mvnw.cmd -B -ntp verify`: 18 pruebas aprobadas en la ejecución conjunta.
Métodos en [BookingNowApplicationTests.java](../../../src/test/java/co/edu/udea/bookingnow/BookingNowApplicationTests.java).
El reporte [SPRINT-1-testing.md](SPRINT-1-testing.md) conserva ubicación, resultado,
fallo corregido y límites de la verificación. No se ejecutaron frontend, Render,
Supabase ni PostgreSQL real. Los mensajes HTTP no acreditan su presentación visual.
