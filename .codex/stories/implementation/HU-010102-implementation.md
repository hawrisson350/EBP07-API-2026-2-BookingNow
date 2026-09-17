# Implementación — HU-010102

- Fecha: 2026-09-17
- Análisis: [HU-010102](../analysis/HU-010102-analysis.md)
- Estado: backend implementado y verificado localmente.

## Cambios realizados y archivos

Registro con razón social y NIT únicos. Antes faltaban formato del NIT, longitud de razón social y sus restricciones únicas.

Componentes implementados o actualizados: ProveedorService; ValidacionCuenta; ProveedorRepositoryAdapter; ProveedorJpaEntity; CorreoRegistradoAdapter; ProveedorController.
Clases Java bajo `src/main/java/co/edu/udea/bookingnow`, en sus capas correspondientes.
Se mantienen dominio, puertos, servicios y adaptadores separados.

## Criterios cubiertos

Ver [matriz de pruebas](../testing/HU-010102-testing.md) para distinguir la parte API
verificada de los criterios que requieren interfaz o despliegue.

## Cambios de contrato y BD

`POST /api/proveedores`. Ver [contrato y ejemplos](../../../README.md) y
[DDL para base nueva](../../../docs/db/sprint-1-schema.sql).
La fixture `src/test/resources/cloud-schema.sql` se actualizó en paralelo.
Las claves únicas y transacciones protegen la integridad ante concurrencia.
No se aplicaron migraciones remotas ni se modificó el documento Word original.

## Verificaciones ejecutadas

Maven `verify`: 18 pruebas, 0 fallos, 0 errores, 0 omitidas; JAR generado.
Evidencia y entorno en [reporte del sprint](../testing/SPRINT-1-testing.md).

## Pendientes y decisiones

Razón social se compara sin mayúsculas ni espacios repetidos. Se valida el formato del NIT, no el cálculo fiscal de su último dígito. No se verifica existencia legal de una empresa.

Falta integración visual y verificación en PostgreSQL real. No se declara
aceptación completa de pantallas ni de tiempos de respuesta del despliegue.
