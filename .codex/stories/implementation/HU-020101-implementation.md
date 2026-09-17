# Implementación — HU-020101

- Fecha: 2026-09-17
- Análisis: [HU-020101](../analysis/HU-020101-analysis.md)
- Estado: backend implementado y verificado localmente.

## Cambios realizados y archivos

Crear y consultar el negocio propio, antes inexistente. El usuario confirmó un único negocio por proveedor en este sprint.

Componentes implementados o actualizados: Negocio; Multimedia; RegistrarNegocioUseCase; ObtenerMiNegocioUseCase; NegocioRepositoryPort; NegocioService; NegocioRepositoryAdapter; NegocioController.
Clases Java bajo `src/main/java/co/edu/udea/bookingnow`, en sus capas correspondientes.
Se mantienen dominio, puertos, servicios y adaptadores separados.

## Criterios cubiertos

Ver [matriz de pruebas](../testing/HU-020101-testing.md) para distinguir la parte API
verificada de los criterios que requieren interfaz o despliegue.

## Cambios de contrato y BD

`GET /api/negocios/mio; POST /api/negocios`. Ver [contrato y ejemplos](../../../README.md) y
[DDL para base nueva](../../../docs/db/sprint-1-schema.sql).
La fixture `src/test/resources/cloud-schema.sql` se actualizó en paralelo.
Las claves únicas y transacciones protegen la integridad ante concurrencia.
No se aplicaron migraciones remotas ni se modificó el documento Word original.

## Verificaciones ejecutadas

Maven `verify`: 18 pruebas, 0 fallos, 0 errores, 0 omitidas; JAR generado.
Evidencia y entorno en [reporte del sprint](../testing/SPRINT-1-testing.md).

## Pendientes y decisiones

Obligatorios provisionales: nombre, correo, contacto, categoría, modalidad; dirección si presencial. Foto y galería opcionales mediante URLs HTTPS, máximo 20 elementos IMAGEN/VIDEO. No hay carga de archivos ni comprobación remota de contenido. Categoría libre; contacto de 7–15 dígitos.

Falta integración visual y verificación en PostgreSQL real. No se declara
aceptación completa de pantallas ni de tiempos de respuesta del despliegue.
