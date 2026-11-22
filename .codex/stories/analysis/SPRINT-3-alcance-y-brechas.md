# Sprint 3 — alcance, análisis y preparación

- Fecha de análisis: 2026-11-21
- Fuente principal: archivo `Inventario-HU-Sprint-3.xlsx` recuperado del chat «Inventario HU Sprint 3»; cada PBI enlaza a Azure DevOps.
- Inventario agregado a `docs/reportes/reporte-hu.xlsx`, hoja `Sprint 3`.
- El desarrollo está iniciado; el detalle implementado y las verificaciones pendientes están abajo.

## Historias identificadas

| ID Azure | HU | Título | Tipo de trabajo inicial |
|---:|---|---|---|
| 33 | HU-060102 | Cancelar reserva | Backend + frontend; el criterio pide ambos lados |
| 31 | HU-060202 | Consultar historial de reservas | Backend + frontend |
| 35 | HU-060301 | Recibir confirmación de reserva | Backend email; requiere configurar Resend |
| 36 | HU-060302 | Recibir recordatorio de reserva | Backend email, 24 horas antes |
| 37 | HU-060303 | Recibir notificación de cancelación | Backend email tras cancelar |
| 109 | HU-060201 | Consultar mis reservas | Backend + frontend |
| 87 | HU-070101 | Generar reportes mediante filtros | Backend + frontend |
| 94 | HU-070102 | Exportar reportes en PDF | Backend genera PDF/XLSX según formato solicitado |
| 140 | HU-A11Y-01 | Navegación por teclado y estructura semántica | Frontend; coordinar errores/mensajes API |
| 141 | HU-A11Y-02 | Formularios accesibles y contraste AA | Frontend; coordinar errores/mensajes API |

## Estado observado en el código

- Spring Boot 4 / Java 21 y arquitectura hexagonal continúan como base del backend.
- Reservas: existe `POST /api/reservas` para registrar y `GET /api/servicios/{idServicio}/disponibilidad` para disponibilidad. La reserva nace con estado `CONFIRMADA`.
- Desarrollo inicial implementado: consultas propias/historial, cancelación con dos horas, reportes PDF/XLSX y emails de confirmación, cancelación y recordatorio 24h antes. Compila; faltan pruebas de comportamiento.
- El contrato SQL local incluye estados adicionales requeridos por el historial/cancelación; no se ha aplicado en PostgreSQL/Supabase y debe revisarlo el repo de BD.
- La disponibilidad excluye reservas canceladas por cliente/proveedor, por lo que el cupo se libera del cálculo.
- La API genera reportes PDF/XLSX en memoria; no conserva archivos. El scheduler de correos queda inactivo hasta configurar Resend.
- Las dos historias de accesibilidad siguen en el alcance de React; servicio de logs pospuesto por decisión del equipo.
- Evidencia de detalle, preguntas y propuesta por HU: los diez documentos `HU-*-analysis.md` y sus fichas en `detail-user-stories/`.

## Decisiones tomadas y pendientes restantes

1. **Cancelación:** cliente dueño, proveedor dueño de negocio o administrador; mínimo dos horas antes. Proveedor/administrador deben indicar el motivo.
2. **Notificaciones:** email acordado; Resend implementado como adaptador. Falta configurar la clave y un remitente permitido y probar entrega.
3. **Recordatorio:** el equipo definió 24 horas; scheduler agregado. Faltan pruebas de plazo, exclusión de canceladas y entrega. Los fallos no se reintentan actualmente.
4. **Reportes:** API con filtros, PDF/XLSX y ámbito de proveedor/admin acordados. Confirmar si agrupación por servicio/recurso y conteo de reservas satisface la métrica buscada.
5. **Accesibilidad y logs:** accesibilidad queda en el frontend; el servicio de logs se abordará luego.

## Secuencia sugerida

1. Probar consultas, cancelación, permisos y scheduler; validar contrato SQL actualizado en una base no productiva.
2. Configurar Resend con clave de envío y remitente permitido; probar confirmación, cancelación y recordatorio por email.
3. Evaluar reintentos de entrega después de instrumentar errores.
4. Revisar contenido/legibilidad de PDF y Excel con frontend y probar filtros.
5. Coordinar accesibilidad con React y registrar evidencia en la UI.

## Evidencia pendiente antes de cerrar las HU

- Decisiones anteriores registradas por el equipo/Azure, sin ambigüedades de estado o permisos.
- Contrato de endpoints y JSON compartido con frontend.
- Para persistencia, revisar el esquema real de PostgreSQL y definir cambios compatibles; no aplicar migraciones desde este análisis.
- Por HU, pruebas que observen cada criterio. Las pruebas PostgreSQL reales son diferentes de las pruebas con H2.

## Riesgos de lectura del inventario

- El ID 33 tiene código HU `HU-060102` en Azure, aunque el sprint previo asoció ese código a otra descripción; usar ID de Azure y título para diferenciar.
- El inventario incluye nombres parecidos entre consulta de activas e historial. Mantener códigos y PBI como claves.
- El archivo descargado se conserva como fuente local bajo `workspace/`, excluido de Git. Las fichas derivadas sí se publican en el repo.
