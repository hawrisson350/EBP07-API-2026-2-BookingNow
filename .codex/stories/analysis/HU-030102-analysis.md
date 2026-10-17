# Análisis — HU-030102: Buscar servicio por nombre y tipo

- Fecha: 2026-10-17
- Ficha: `../detail-user-stories/HU-030102-*.md`
- Estimación inicial de backend: **8 puntos de historia (Media incertidumbre)**.
- Estado: análisis inicial; ver alcance y dependencias del [Sprint 2](SPRINT-2-estimacion-y-alcance.md).

## Requisitos confirmados

Ver descripción y criterios transcritos desde Azure DevOps en la ficha de origen. Esta estimación cubre API y persistencia local; no estima frontend.

## Estado actual y propuesta

La implementación se encuentra registrada en `.codex/stories/implementation/` y mantiene los puertos de entrada agrupados por entidad, los servicios de aplicación y los adaptadores REST/JPA. Las verificaciones pendientes están separadas de la compilación en la evidencia de testing.

## Estimación y alcance

- **Tamaño:** 8 SP (Media).
- **Incluye:** Búsqueda parcial por nombre, filtro por tipo, paginación, resultados vacíos. Servicio no tiene tipo; datos, orden y filtros necesitan acordarse. Requiere endpoint de catálogo público.
- **Excluye:** cambios al frontend, despliegue, escritura directa en Supabase y requisitos no descritos por Azure.

## Dudas y dependencias

Búsqueda parcial por nombre, filtro por tipo, paginación, resultados vacíos. Servicio no tiene tipo; datos, orden y filtros necesitan acordarse. Requiere endpoint de catálogo público.

Las decisiones que cambien datos o contrato se registrarán aquí; los criterios ausentes no se completan por inferencia.

## Contrato HTTP y persistencia

Contrato implementado: revisar rutas en Swagger/README y el esquema propuesto en `docs/db/sprint-2-schema.sql`. No se ejecutó sobre la base compartida.

## Matriz de criterios

| Criterio | Cambio previsto | Comprobación prevista |
|---|---|---|
| Criterios completos de la ficha | Dominio + caso de uso + adapter REST/JPA | Pruebas de éxito, validación, permisos y persistencia |

## Pendientes

Confirmar ambigüedades listadas en el documento de estimación y alcance; registrar cualquier supuesto técnico usado durante la implementación.
