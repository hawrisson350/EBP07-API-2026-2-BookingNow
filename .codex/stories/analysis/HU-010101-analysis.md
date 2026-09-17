# Análisis — HU-010101

- Fecha: 2026-09-17
- Ficha: [requisitos originales](../detail-user-stories/HU-010101-Registro-de-cliente.md)
- Estado: análisis aplicado al backend; aceptación completa sujeta a pendientes.

## Requisitos confirmados y diferencias

Registro con correo globalmente único, contraseña segura y confirmación. Antes faltaban complejidad y unicidad de correo.

## Desglose por capa hexagonal

1. Dominio: modelos y reglas de Cliente; sin HTTP/JPA.
2. Entrada: comandos y casos de uso agrupados por entidad en `application/port/in`.
3. Aplicación: validar datos, permisos y relaciones; invocar puertos de salida.
4. Salida: repositorios JPA con restricciones de integridad en infraestructura.
5. HTTP: DTO sin hashes, estado de respuesta y documentación Swagger.
6. Verificación: escenarios HTTP reales con H2 y contrato de esquema cloud.

## Decisiones técnicas y supuestos

Se mantiene nombreUsuario de 3–50 caracteres ASCII permitidos. Correo único entre roles para identificar la cuenta sin selector. Estos detalles son decisiones técnicas, no criterios nuevos.

## Contrato HTTP y persistencia

`POST /api/clientes`. Contrato de BD: [sprint-1-schema.sql](../../../docs/db/sprint-1-schema.sql).
Las mutaciones de registro responden 201 con `mensaje` y `datos`; login devuelve
JWT y cuenta. Datos inválidos 400, autenticación 401, permisos 403, inexistencia
404 y duplicados 409 según endpoint. Consultar README para ejemplos completos.

## Matriz de criterios

| Criterio | Comportamiento | Comprobación |
|---|---|---|
| CA-01 | Cuenta creada, mensaje y BCrypt | `registroLoginCorreoYTitularidad` |
| CA-02 | Rechazar correo inválido | `validaRegistroYComplejidad` |
| CA-03 | Rechazar contraseña insegura | `validaRegistroYComplejidad` |
| CA-04 | Rechazar nombre inválido | `validaRegistroYComplejidad` |
| CA-05 | Rechazar correo registrado en cualquier rol | `correoUnicoEntreAmbosRoles; concurrenciaCorreoYNegocioMantienenUnicidad` |

## Dependencias y alcance pendiente

Las pantallas, mensajes visibles y navegación se integran en el frontend.
La conexión y migración de PostgreSQL se coordinan con el repositorio de BD;
H2 no acredita compatibilidad completa con ese motor.
