# Análisis — HU-010201

- Fecha: 2026-09-17
- Ficha: [requisitos originales](../detail-user-stories/HU-010201-Inicio-de-sesion.md)
- Estado: análisis aplicado al backend; aceptación completa sujeta a pendientes.

## Requisitos confirmados y diferencias

Login común por correo, identificación del rol, estado de cuenta y errores genéricos. El contrato anterior usaba nombreUsuario.

## Desglose por capa hexagonal

1. Dominio: modelos y reglas de Autenticación; sin HTTP/JPA.
2. Entrada: comandos y casos de uso agrupados por entidad en `application/port/in`.
3. Aplicación: validar datos, permisos y relaciones; invocar puertos de salida.
4. Salida: repositorios JPA con restricciones de integridad en infraestructura.
5. HTTP: DTO sin hashes, estado de respuesta y documentación Swagger.
6. Verificación: escenarios HTTP reales con H2 y contrato de esquema cloud.

## Decisiones técnicas y supuestos

Las cuentas nuevas nacen ACTIVA. No se añade administración pública de estados. El rol se identifica con registro global de correo. Alias de login por tipo conservados, también reciben correo. JWT/BCrypt no sustituyen el ocultamiento visual de CA-09.

## Contrato HTTP y persistencia

`POST /api/auth/login`. Contrato de BD: [sprint-1-schema.sql](../../../docs/db/sprint-1-schema.sql).
Las mutaciones de registro responden 201 con `mensaje` y `datos`; login devuelve
JWT y cuenta. Datos inválidos 400, autenticación 401, permisos 403, inexistencia
404 y duplicados 409 según endpoint. Consultar README para ejemplos completos.

## Matriz de criterios

| Criterio | Comportamiento | Comprobación |
|---|---|---|
| CA-01 | Autenticar cuenta activa y emitir JWT | `registroLoginCorreoYTitularidad` |
| CA-02 | Contraseña incorrecta: mensaje genérico | `loginNoRevelaExistenciaYValidaCampos` |
| CA-03 | Correo desconocido: mismo mensaje | `loginNoRevelaExistenciaYValidaCampos` |
| CA-04 | Identificar campos faltantes | `loginNoRevelaExistenciaYValidaCampos` |
| CA-05 | Rechazar formato inválido | `loginNoRevelaExistenciaYValidaCampos` |
| CA-06 | Devolver rol CLIENTE/PROVEEDOR | `registroLoginCorreoYTitularidad` |
| CA-07 | Impedir operaciones de otros roles/titulares | `registroLoginCorreoYTitularidad; negocioValidaObligatoriosFormatosYPermisos; servicioValidaNumerosCamposYNegocioPropio` |
| CA-08 | Denegar cuenta inactiva/bloqueada | `cuentaNoHabilitadaNoIniciaNiReutilizaToken` |
| CA-09 | Ocultar contraseña mientras se escribe | `Requiere frontend` |
| CA-10 | Resultado máximo 3 segundos | `registroLoginCorreoYTitularidad` |

## Dependencias y alcance pendiente

Las pantallas, mensajes visibles y navegación se integran en el frontend.
La conexión y migración de PostgreSQL se coordinan con el repositorio de BD;
H2 no acredita compatibilidad completa con ese motor.
