# Pruebas — HU-060201 Consultar mis reservas

- Fecha: 2026-11-22
- Ficha e implementación: HU-060201, Azure PBI 109.
- Entorno y revisión probada: compilación Java/Maven local; no se probó comportamiento HTTP.
- Estado: pendiente

## Resultados por criterio

| Criterio | Prueba o comando | Resultado real | Evidencia |
|---|---|---|---|
| Listar reservas futuras activas/confirmadas y orden ascendente | No ejecutada | Pendiente | Ninguna |
| Acceso a acciones y datos visibles | No ejecutada | Pendiente | Ninguna |
| Lista vacía | No ejecutada | Pendiente | Ninguna |

## Comandos ejecutados

- `mvnw -DskipTests compile` — `BUILD SUCCESS`; valida compilación, no el comportamiento de la HU.

## Fallos y correcciones

No aplica; no se ejecutaron pruebas de comportamiento.

## Comprobaciones no ejecutadas

- JWT ausente/incorrecto y rol distinto de cliente.
- Separación de reservas entre dos clientes.
- Filtro de fecha/estado, orden, campos de servicio/negocio y lista vacía.
- Compatibilidad de consultas con PostgreSQL real.

## Limitaciones y estado final

Implementación compilada pero no verificada. No declarar la HU completa hasta ejecutar las comprobaciones anteriores.
