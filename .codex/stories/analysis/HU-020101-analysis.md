# Análisis — HU-020101

- Fecha: 2026-09-17
- Ficha: [requisitos originales](../detail-user-stories/HU-020101-Registro-de-negocio.md)
- Estado: análisis aplicado al backend; aceptación completa sujeta a pendientes.

## Requisitos confirmados y diferencias

Crear y consultar el negocio propio, antes inexistente. El usuario confirmó un único negocio por proveedor en este sprint.

## Desglose por capa hexagonal

1. Dominio: modelos y reglas de Negocio; sin HTTP/JPA.
2. Entrada: comandos y casos de uso agrupados por entidad en `application/port/in`.
3. Aplicación: validar datos, permisos y relaciones; invocar puertos de salida.
4. Salida: repositorios JPA con restricciones de integridad en infraestructura.
5. HTTP: DTO sin hashes, estado de respuesta y documentación Swagger.
6. Verificación: escenarios HTTP reales con H2 y contrato de esquema cloud.

## Decisiones técnicas y supuestos

Obligatorios provisionales: nombre, correo, contacto, categoría, modalidad; dirección si presencial. Foto y galería opcionales mediante URLs HTTPS, máximo 20 elementos IMAGEN/VIDEO. No hay carga de archivos ni comprobación remota de contenido. Categoría libre; contacto de 7–15 dígitos.

## Contrato HTTP y persistencia

`GET /api/negocios/mio; POST /api/negocios`. Contrato de BD: [sprint-1-schema.sql](../../../docs/db/sprint-1-schema.sql).
Las mutaciones de registro responden 201 con `mensaje` y `datos`; login devuelve
JWT y cuenta. Datos inválidos 400, autenticación 401, permisos 403, inexistencia
404 y duplicados 409 según endpoint. Consultar README para ejemplos completos.

## Matriz de criterios

| Criterio | Comportamiento | Comprobación |
|---|---|---|
| CA-01 | Informar si puede registrar | `negocioVirtualGaleriaYUnicoProveedor` |
| CA-02 | Impedir segundo negocio | `negocioVirtualGaleriaYUnicoProveedor; concurrenciaCorreoYNegocioMantienenUnicidad` |
| CA-03 | Guardar información y galería | `negocioVirtualGaleriaYUnicoProveedor; negocioValidaObligatoriosFormatosYPermisos` |
| CA-04 | Virtual sin dirección física | `negocioVirtualGaleriaYUnicoProveedor` |
| CA-05 | Identificar campos obligatorios | `negocioValidaObligatoriosFormatosYPermisos` |
| CA-06 | Rechazar formatos inválidos | `negocioValidaObligatoriosFormatosYPermisos` |
| CA-07 | Asociar al proveedor autenticado | `negocioVirtualGaleriaYUnicoProveedor; negocioValidaObligatoriosFormatosYPermisos` |
| CA-08 | Confirmar creación | `negocioVirtualGaleriaYUnicoProveedor` |

## Dependencias y alcance pendiente

Las pantallas, mensajes visibles y navegación se integran en el frontend.
La conexión y migración de PostgreSQL se coordinan con el repositorio de BD;
H2 no acredita compatibilidad completa con ese motor.
