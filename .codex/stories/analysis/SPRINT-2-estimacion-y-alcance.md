# Sprint 2 — estimación y alcance del backend

- Fecha: 2026-10-17
- Método: puntos de historia Fibonacci (1, 2, 3, 5, 8, 13); estimación inicial del backend/API.
- Estado: estimación de referencia; no equivale a compromiso ni a capacidad aprobada del equipo.

Las estimaciones consideran dominio, puertos hexagonales, servicios, adaptadores REST/JPA, documentación de contrato y verificación local. No incluyen frontend, despliegue, migración ejecutada en Supabase ni integración real de correo. La capacidad del equipo y el Definition of Done no se proporcionaron.

| HU | Alcance resumido | Estimación inicial | Estado backend actual |
|---|---|---:|---|
| [HU-060101 — Registrar reserva con fecha y hora](HU-060101-analysis.md) | Disponibilidad, reserva para cliente, horario, recursos y concurrencia | 13 SP (Alta) | API parcial: flujo y persistencia propuestos; correo, frontend y comprobación en PostgreSQL pendientes. |
| [HU-040101 — Registrar recurso](HU-040101-analysis.md) | Registrar y listar recurso por negocio | 5 SP (Media) | API implementada; validaciones y permisos compilan, comportamiento aún no probado. |
| [HU-030101 — Listar información de servicio](HU-030101-Sprint-2-analysis.md) | Detalle público, negocio e imágenes | 5 SP (Media) | API de detalle implementada; tipo libre/opcional; carrusel e inicio de reserva quedan al frontend. |
| [HU-030102 — Buscar servicio por nombre y tipo](HU-030102-analysis.md) | Filtros combinados, paginación y resultado vacío | 8 SP (Media) | API implementada; tipo libre/opcional; controles y mensaje de lista vacía quedan al frontend. |
| [HU-050101 — Registrar empleado](HU-050101-analysis.md) | Registrar/listar empleados por negocio | 5 SP (Media) | API implementada; formato técnico de documento/teléfono asumido; pruebas y posible límite requieren seguimiento. |
| [HU-030202 — Asignar empleados y recursos a un servicio](HU-030202-analysis.md) | Reemplazar asignaciones y validar pertenencia | 8 SP (Alta) | API implementada; pruebas funcionales y PostgreSQL pendientes. |
| [HU-020202 — Configurar horario de atención del negocio](HU-020202-analysis.md) | Horario semanal, validación, consulta y modificación | 8 SP (Alta) | API implementada por ruta separada; integrarlo al alta y consulta del negocio queda pendiente. |

**Total orientativo inicial: 52 puntos de historia.** Esta suma no debe asumirse como un sprint viable hasta contrastarla con velocidad/capacidad del equipo. No es una medida de esfuerzo real ni una estimación posterior a la implementación.

## Supuestos y brechas que se conservan visibles

- Azure tiene dos PBIs con identificador HU-030101: la historia de Sprint 1 de registrar servicio y la de Sprint 2 de consultar información de servicio. Se conserva el identificador que aparece en el tablero y se usa el título para desambiguar; el equipo debería corregir la clave duplicada.
- El PBI HU-030101 del Sprint 2 solicita tipo/categoría y varias imágenes. La API admite `tipoServicio` como texto libre opcional y una galería Base64; no impone catálogo ni implementa el carrusel visual.
- El PBI HU-030102 pide tipo/categoría, pero no define catálogo de tipos de servicio. La búsqueda usa el texto libre opcional `tipoServicio`; no se inventó una taxonomía.
- El PBI HU-060101 usa un identificador normalmente asociado a reservas; el título, descripción y criterios de Azure indican reserva. Se mantiene la fuente Azure.
- Reserva menciona notificación automática por correo, pero el proyecto carece de adaptador/proveedor de correo y configuración. No se reportará como terminada solo con guardar la reserva.
- Se mantiene PostgreSQL como destino. Cambios de esquema se documentan para el repositorio de BD; no se ejecutan contra una BD remota. Por tanto, la compilación no confirma que el esquema esté aplicado ni sea compatible con la instancia compartida.
- La implementación y las brechas por criterio se detallan en [implementación Sprint 2](../implementation/SPRINT-2.md). La verificación ejecutada está en [testing Sprint 2](../testing/SPRINT-2.md); compilación no equivale a criterios probados.

## Alcance por incremento recomendado

1. Datos del negocio: horario, recurso y empleado.
2. Servicio: búsqueda pública, detalle y asignaciones.
3. Reserva: validar horario/recursos/empleados y evitar doble reserva; correo de confirmación queda pendiente hasta definir proveedor.

## Referencias

Fichas originales en `detail-user-stories/`; análisis individual por HU enlazado arriba.
