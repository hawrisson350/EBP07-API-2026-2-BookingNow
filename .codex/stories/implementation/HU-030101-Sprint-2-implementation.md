# Implementación — HU-030101 Listar información de servicio (Sprint 2)

> Azure repite el identificador HU-030101 para “Registrar servicio” en Sprint 1. Esta ficha corresponde a “Listar información de servicio”, PBI 62.

- Alcance: API backend, Sprint 2.
- Implementado: detalle público de un servicio con información del negocio, tipo opcional e imágenes Base64; también se conserva la imagen de referencia individual por compatibilidad.
- Ruta: `GET /api/servicios/{idServicio}`. El detalle incluye `nombreNegocio` y `categoriaNegocio`.
- Evidencia de código: `ObtenerServicioDetalleUseCase`, `ServicioService`, `ServicioDetalle`, `ServicioCatalogoController` y `ServicioCatalogoResponse`.
- Persistencia: tipo e imágenes adicionales en `docs/db/sprint-2-schema.sql`; no se ha aplicado en Supabase.
- Límites: el negocio no tiene una taxonomía de tipo de servicio; `tipoServicio` es texto libre. Carrusel y acción de iniciar reserva son responsabilidades del frontend.
- Estado: detalle backend implementado; experiencia completa de selección/carrusel pendiente del frontend; verificación de comportamiento pendiente.

## Criterios

| Criterio | Estado backend |
|---|---|
| Obtener la ficha del servicio seleccionado | Implementado por ID; servicio inexistente responde 404 |
| Nombre, tipo, duración, precio y descripción | Implementado; el tipo es opcional |
| Imágenes asociadas | Implementado en respuesta, una o varias |
| Identificar el negocio | Implementado con nombre e información de categoría del negocio |
| Carrusel e inicio del proceso de reserva | Pendiente del frontend |
| Mostrar solo el servicio seleccionado | Implementado |
