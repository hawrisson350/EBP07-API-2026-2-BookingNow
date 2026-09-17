# Sprint 1 — implementación de API

Fecha: 2026-09-17. Fuente: las cinco fichas extraídas de `workspace/Recuento HU.docx`.
El usuario autorizó implementar el sprint y confirmó un único negocio por proveedor.

| HU | Entrega API | Detalle |
|---|---|---|
| HU-010101 | Registro de cliente, contraseña segura y correo único | [Implementación](HU-010101-implementation.md) |
| HU-010102 | Registro de proveedor, razón social y NIT únicos | [Implementación](HU-010102-implementation.md) |
| HU-010201 | Login por correo, JWT, rol y estado de cuenta | [Implementación](HU-010201-implementation.md) |
| HU-020101 | Un negocio por proveedor, modalidad virtual y galería | [Implementación](HU-020101-implementation.md) |
| HU-030101 | Registro/listado de servicios propios | [Implementación](HU-030101-implementation.md) |

Cada ficha tiene análisis, implementación y pruebas con trazabilidad de sus 40
criterios. Las filas parcialmente verificadas no equivalen a aceptación completa.
No se añadieron historias inventadas al documento original.

## Integración

- Login común: `POST /api/auth/login`, cuerpo `correo` y `contrasena`.
- Registros: HTTP 201 con `mensaje` y `datos` (cambio de forma de respuesta).
- El correo es único entre ambos tipos de cuenta y se normaliza a minúsculas.
- El frontend usa `cuenta.rol` y `GET /api/negocios/mio` para elegir opciones.
- Multimedia se representa con URLs HTTPS; no hay servicio de subida de archivos.
- La base nueva requiere las seis tablas de `docs/db/sprint-1-schema.sql`.
- Bases existentes requieren migración/backfill desde el repositorio de BD.

## Verificación y pendientes

[18 pruebas aprobadas y límites de la evidencia](../testing/SPRINT-1-testing.md).
Pendientes: pantallas y ocultamiento visual de contraseña, probar PostgreSQL,
aplicar migraciones, despliegue y tiempo de respuesta real. Reservas no forma
parte de estas cinco HU; los servicios quedan ACTIVO para esa integración futura.
