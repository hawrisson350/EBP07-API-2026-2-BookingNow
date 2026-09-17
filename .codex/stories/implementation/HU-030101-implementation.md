# Implementación — HU-030101

- Fecha: 2026-09-17
- Análisis: [HU-030101](../analysis/HU-030101-analysis.md)
- Estado: backend implementado y verificado localmente.

## Cambios realizados y archivos

Registrar y listar servicios del negocio propio, antes inexistentes. La cardinalidad de negocio es uno por proveedor por decisión del usuario.

Componentes implementados o actualizados: Servicio; RegistrarServicioUseCase; ListarServiciosUseCase; ServicioRepositoryPort; ServicioService; ServicioRepositoryAdapter; ServicioController.
Clases Java bajo `src/main/java/co/edu/udea/bookingnow`, en sus capas correspondientes.
Se mantienen dominio, puertos, servicios y adaptadores separados.

## Criterios cubiertos

Ver [matriz de pruebas](../testing/HU-030101-testing.md) para distinguir la parte API
verificada de los criterios que requieren interfaz o despliegue.

## Cambios de contrato y BD

`POST y GET /api/negocios/{idNegocio}/servicios`. Ver [contrato y ejemplos](../../../README.md) y
[DDL para base nueva](../../../docs/db/sprint-1-schema.sql).
La fixture `src/test/resources/cloud-schema.sql` se actualizó en paralelo.
Las claves únicas y transacciones protegen la integridad ante concurrencia.
No se aplicaron migraciones remotas ni se modificó el documento Word original.

## Verificaciones ejecutadas

Maven `verify`: 18 pruebas, 0 fallos, 0 errores, 0 omitidas; JAR generado.
Evidencia y entorno en [reporte del sprint](../testing/SPRINT-1-testing.md).

## Pendientes y decisiones

Imagen opcional por URL HTTPS. Duración en minutos enteros según modelo provisional. Precio BigDecimal/numeric(12,2), máximo 9999999999.99; no se infiere moneda. Nombre máximo 255 y descripción 2000. No se construye el módulo de reservas.

Falta integración visual y verificación en PostgreSQL real. No se declara
aceptación completa de pantallas ni de tiempos de respuesta del despliegue.
