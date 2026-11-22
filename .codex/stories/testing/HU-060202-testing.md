# Pruebas — HU-060202 Consultar historial de reservas

- Fecha: 2026-11-22
- Ficha e implementación: HU-060202, Azure PBI 31.
- Entorno y revisión probada: compilación Java/Maven local; no se probó comportamiento HTTP.
- Estado: pendiente

## Resultados por criterio

| Criterio | Prueba o comando | Resultado real | Evidencia |
|---|---|---|---|
| Listar estados completado, cancelado o no asistió | No ejecutada | Pendiente | Ninguna |
| Orden descendente y campos requeridos | No ejecutada | Pendiente | Ninguna |
| Lista vacía | No ejecutada | Pendiente | Ninguna |

## Comandos ejecutados

- `mvnw -DskipTests compile` — `BUILD SUCCESS`; valida compilación, no el comportamiento de la HU.

## Fallos y correcciones

No aplica; no se ejecutaron pruebas de comportamiento.

## Comprobaciones no ejecutadas

- JWT ausente/incorrecto y rol distinto de cliente.
- Separación de historial entre dos clientes.
- Filtro por los tres estados, orden, servicio/lugar y lista vacía.
- Compatibilidad de consultas con PostgreSQL real.

## Limitaciones y estado final

Implementación compilada pero no verificada. No declarar la HU completa hasta ejecutar las comprobaciones anteriores.
