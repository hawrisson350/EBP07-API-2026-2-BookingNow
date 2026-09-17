# Análisis — HU-030101

- Fecha: 2026-09-17
- Ficha: [requisitos originales](../detail-user-stories/HU-030101-Registrar-servicio.md)
- Estado: análisis aplicado al backend; aceptación completa sujeta a pendientes.

## Requisitos confirmados y diferencias

Registrar y listar servicios del negocio propio, antes inexistentes. La cardinalidad de negocio es uno por proveedor por decisión del usuario.

## Desglose por capa hexagonal

1. Dominio: modelos y reglas de Servicio; sin HTTP/JPA.
2. Entrada: comandos y casos de uso agrupados por entidad en `application/port/in`.
3. Aplicación: validar datos, permisos y relaciones; invocar puertos de salida.
4. Salida: repositorios JPA con restricciones de integridad en infraestructura.
5. HTTP: DTO sin hashes, estado de respuesta y documentación Swagger.
6. Verificación: escenarios HTTP reales con H2 y contrato de esquema cloud.

## Decisiones técnicas y supuestos

Imagen opcional por URL HTTPS. Duración en minutos enteros según modelo provisional. Precio BigDecimal/numeric(12,2), máximo 9999999999.99; no se infiere moneda. Nombre máximo 255 y descripción 2000. No se construye el módulo de reservas.

## Contrato HTTP y persistencia

`POST y GET /api/negocios/{idNegocio}/servicios`. Contrato de BD: [sprint-1-schema.sql](../../../docs/db/sprint-1-schema.sql).
Las mutaciones de registro responden 201 con `mensaje` y `datos`; login devuelve
JWT y cuenta. Datos inválidos 400, autenticación 401, permisos 403, inexistencia
404 y duplicados 409 según endpoint. Consultar README para ejemplos completos.

## Matriz de criterios

| Criterio | Comportamiento | Comprobación |
|---|---|---|
| CA-01 | Exigir negocio propio | `servicioValidaNumerosCamposYNegocioPropio` |
| CA-02 | Nombre/duración/precio/descripción | `servicioSeAsociaYListaConPrecioCero; servicioValidaNumerosCamposYNegocioPropio` |
| CA-03 | Permitir referencia de imagen | `servicioSeAsociaYListaConPrecioCero; servicioValidaNumerosCamposYNegocioPropio` |
| CA-04 | Duración mayor a cero | `servicioValidaNumerosCamposYNegocioPropio` |
| CA-05 | Precio no negativo | `servicioSeAsociaYListaConPrecioCero; servicioValidaNumerosCamposYNegocioPropio` |
| CA-06 | Rechazar información inválida | `servicioValidaNumerosCamposYNegocioPropio` |
| CA-07 | Asociar negocio y proveedor | `servicioSeAsociaYListaConPrecioCero; servicioValidaNumerosCamposYNegocioPropio` |
| CA-08 | Listar y dejar disponible para reservas | `servicioSeAsociaYListaConPrecioCero` |

## Dependencias y alcance pendiente

Las pantallas, mensajes visibles y navegación se integran en el frontend.
La conexión y migración de PostgreSQL se coordinan con el repositorio de BD;
H2 no acredita compatibilidad completa con ese motor.
