# Verificación — HU-030102 Buscar servicio por nombre y tipo

- Estado: **criterios de búsqueda backend cubiertos en H2**.
- Prueba ejecutada: `buscaYConsultaDetalleDeServicioConTipoEGaleria` dentro de `.\mvnw.cmd verify`; suite aprobada.
- Evidencia: filtros por nombre y tipo separados/combinados, coincidencia parcial, paginación de dos resultados, contenido vacío y HTTP 200.
- No ejecutado: integración frontend ni PostgreSQL/Supabase. El mensaje visual de lista vacía pertenece al frontend.
