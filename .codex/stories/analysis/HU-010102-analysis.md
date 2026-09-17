# Análisis — HU-010102

- Fecha: 2026-09-17
- Ficha: [requisitos originales](../detail-user-stories/HU-010102-Registro-de-proveedor.md)
- Estado: análisis aplicado al backend; aceptación completa sujeta a pendientes.

## Requisitos confirmados y diferencias

Registro con razón social y NIT únicos. Antes faltaban formato del NIT, longitud de razón social y sus restricciones únicas.

## Desglose por capa hexagonal

1. Dominio: modelos y reglas de Proveedor; sin HTTP/JPA.
2. Entrada: comandos y casos de uso agrupados por entidad en `application/port/in`.
3. Aplicación: validar datos, permisos y relaciones; invocar puertos de salida.
4. Salida: repositorios JPA con restricciones de integridad en infraestructura.
5. HTTP: DTO sin hashes, estado de respuesta y documentación Swagger.
6. Verificación: escenarios HTTP reales con H2 y contrato de esquema cloud.

## Decisiones técnicas y supuestos

Razón social se compara sin mayúsculas ni espacios repetidos. Se valida el formato del NIT, no el cálculo fiscal de su último dígito. No se verifica existencia legal de una empresa.

## Contrato HTTP y persistencia

`POST /api/proveedores`. Contrato de BD: [sprint-1-schema.sql](../../../docs/db/sprint-1-schema.sql).
Las mutaciones de registro responden 201 con `mensaje` y `datos`; login devuelve
JWT y cuenta. Datos inválidos 400, autenticación 401, permisos 403, inexistencia
404 y duplicados 409 según endpoint. Consultar README para ejemplos completos.

## Matriz de criterios

| Criterio | Comportamiento | Comprobación |
|---|---|---|
| CA-01 | Cuenta de proveedor creada y mensaje | `registroLoginCorreoYTitularidad` |
| CA-02 | Correo válido | `validaRegistroYComplejidad` |
| CA-03 | Razón social mínimo 3 caracteres | `validaYEvitaNitYRazonDuplicados` |
| CA-04 | NIT de 9 dígitos-guion-1 dígito | `validaYEvitaNitYRazonDuplicados` |
| CA-05 | Contraseña segura | `validaRegistroYComplejidad` |
| CA-06 | Nombre de usuario válido | `validaRegistroYComplejidad` |
| CA-07 | Correo global único | `correoUnicoEntreAmbosRoles; concurrenciaCorreoYNegocioMantienenUnicidad` |
| CA-08 | Razón social única normalizada | `validaYEvitaNitYRazonDuplicados` |
| CA-09 | NIT único | `validaYEvitaNitYRazonDuplicados` |

## Dependencias y alcance pendiente

Las pantallas, mensajes visibles y navegación se integran en el frontend.
La conexión y migración de PostgreSQL se coordinan con el repositorio de BD;
H2 no acredita compatibilidad completa con ese motor.
