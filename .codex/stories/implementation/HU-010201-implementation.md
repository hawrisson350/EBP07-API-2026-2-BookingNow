# Implementación — HU-010201

- Fecha: 2026-09-17
- Análisis: [HU-010201](../analysis/HU-010201-analysis.md)
- Estado: backend implementado y verificado localmente.

## Cambios realizados y archivos

Login común por correo, identificación del rol, estado de cuenta y errores genéricos. El contrato anterior usaba nombreUsuario.

Componentes implementados o actualizados: AutenticacionService; ValidacionLogin; ClienteService; ProveedorService; AuthController; JwtConfig; SecurityConfig; EstadoCuenta.
Clases Java bajo `src/main/java/co/edu/udea/bookingnow`, en sus capas correspondientes.
Se mantienen dominio, puertos, servicios y adaptadores separados.

## Criterios cubiertos

Ver [matriz de pruebas](../testing/HU-010201-testing.md) para distinguir la parte API
verificada de los criterios que requieren interfaz o despliegue.

## Cambios de contrato y BD

`POST /api/auth/login`. Ver [contrato y ejemplos](../../../README.md) y
[DDL para base nueva](../../../docs/db/sprint-1-schema.sql).
La fixture `src/test/resources/cloud-schema.sql` se actualizó en paralelo.
Las claves únicas y transacciones protegen la integridad ante concurrencia.
No se aplicaron migraciones remotas ni se modificó el documento Word original.

## Verificaciones ejecutadas

Maven `verify`: 18 pruebas, 0 fallos, 0 errores, 0 omitidas; JAR generado.
Evidencia y entorno en [reporte del sprint](../testing/SPRINT-1-testing.md).

## Pendientes y decisiones

Las cuentas nuevas nacen ACTIVA. No se añade administración pública de estados. El rol se identifica con registro global de correo. Alias de login por tipo conservados, también reciben correo. JWT/BCrypt no sustituyen el ocultamiento visual de CA-09.

Falta integración visual y verificación en PostgreSQL real. No se declara
aceptación completa de pantallas ni de tiempos de respuesta del despliegue.
