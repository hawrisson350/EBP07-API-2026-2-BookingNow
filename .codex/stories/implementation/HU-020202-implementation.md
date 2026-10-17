# Implementación — HU-020202 Configurar horario de atención

- Alcance: API backend, Sprint 2.
- Implementado: configurar/reemplazar el horario semanal, consultarlo, validar días repetidos, campos requeridos y cierre posterior a apertura; el proveedor solo puede actuar sobre su negocio.
- Rutas: `PUT/GET /api/negocios/{idNegocio}/horarios`.
- Evidencia de código: `HorarioAtencionService`, puertos de entrada/salida, `HorarioAtencionJpaEntity`, `HorarioAtencionRepositoryAdapter` y `HorarioAtencionController`.
- Persistencia: tabla `bookingnow.horarios_atencion` en `docs/db/sprint-2-schema.sql`; no se ha aplicado en Supabase.
- Diferencia frente a la HU: se configura después de registrar el negocio, no dentro del mismo formulario/transacción. No se devuelve embebido en la consulta de negocio; hay una ruta específica.
- Estado: implementación backend parcial respecto al flujo integral; verificación de comportamiento pendiente.

## Criterios

| Criterio | Estado backend |
|---|---|
| Seleccionar días y enviar apertura/cierre por día | Implementado mediante API |
| Permitir días sin atención omitiéndolos | Implementado al reemplazar la lista semanal |
| Rechazar cierre igual o anterior a apertura | Implementado |
| Guardar asociado al negocio y permitir modificarlo | Implementado mediante PUT |
| Consultar horario | Implementado mediante GET específico |
| Integrarlo al registro/consulta de negocio | Pendiente; actualmente es un paso y recurso separado |
| Formularios y mensajes visuales | Pendiente del frontend |
