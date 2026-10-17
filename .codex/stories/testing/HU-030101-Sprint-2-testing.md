# Verificación — HU-030101 Listar información de servicio (Sprint 2, PBI 62)

- Estado: **criterios de consulta backend cubiertos en H2**. Azure también usa HU-030101 para otra historia de Sprint 1; consultar por título/PBI.
- Prueba ejecutada: `buscaYConsultaDetalleDeServicioConTipoEGaleria` dentro de `.\mvnw.cmd verify`; suite aprobada.
- Evidencia: detalle HTTP por ID, 404, tipo, dos imágenes, identificación del negocio y categoría del negocio.
- No ejecutado: PostgreSQL/Supabase ni integración frontend. Carrusel e inicio de reserva requieren frontend; `tipoServicio` es texto libre opcional.
