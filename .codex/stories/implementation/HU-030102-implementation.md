# Implementación — HU-030102 Buscar servicio por nombre y tipo

- Alcance: API backend, Sprint 2.
- Implementado: filtros opcionales parciales y sin distinción de mayúsculas para nombre/tipo, combinables, con paginación. Sin coincidencias se devuelve una página con `contenido: []`.
- Ruta: `GET /api/servicios?nombre=&tipo=&pagina=0&tamano=20` (límite de tamaño: 100).
- Evidencia de código: `BuscarServiciosUseCase`, `PaginaServicios`, `ServicioService`, `ServicioRepositoryPort`, `ServicioJpaRepository`, `ServicioRepositoryAdapter`, `ServicioCatalogoController` y `ServicioResumenResponse`.
- Tipo: texto opcional sin catálogo predefinido, conforme a la indefinición de Azure.
- Estado: API implementada; selección de tipo, mensaje de “sin resultados” y navegación de páginas corresponden al frontend; verificación de comportamiento pendiente.

## Criterios

| Criterio | Estado backend |
|---|---|
| Buscar palabra o parte del nombre | Implementado |
| Filtrar por tipo | Implementado como coincidencia parcial de texto |
| Combinar ambos filtros | Implementado con intersección (AND) |
| Mostrar coincidencias y paginar | Implementado |
| Informar que no hay coincidencias | API devuelve lista vacía; texto de interfaz pendiente del frontend |
| Seleccionar resultado para consultar información | Ruta de detalle disponible; integración pendiente del frontend |
