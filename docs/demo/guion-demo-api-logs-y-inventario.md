# BookingNow — guion de demo, pruebas e inventario funcional

Guía para recorrer la API desde Swagger y presentar el panel de logs. Los
endpoints y ejemplos se contrastaron con los controladores actuales. Las pruebas
automatizadas se ejecutaron localmente con H2; esta guía no afirma que cada
criterio esté probado contra PostgreSQL.

## URLs de la demo

- API y Swagger: <http://ec2-3-136-161-165.us-east-2.compute.amazonaws.com:8080/swagger-ui.html>
- Panel de observabilidad del backend: <http://ec2-3-136-161-165.us-east-2.compute.amazonaws.com:8080/>
- Health: <http://ec2-3-136-161-165.us-east-2.compute.amazonaws.com:8080/health>
- Front React del equipo: <https://booking-now-theta.vercel.app/>

En Swagger, ejecutar `POST /api/auth/login` con la cuenta correspondiente y
copiar solo `accessToken` en **Authorize → bearerAuth**. No grabar contraseñas,
tokens, claves ni valores de configuración en el video.

## Recorrido sugerido para el video

### 1. Mostrar que la API está arriba

1. Abrir `/health`; resultado esperado: `200` y `{"status":"UP"}`.
2. Abrir Swagger y explicar que es el catálogo interactivo del backend.
3. Mostrar `GET /api/servicios` sin iniciar sesión: el catálogo es público.

### 2. Reservar y recibir la confirmación

1. Iniciar sesión en la cuenta **CLIENTE** cuyo correo es el Gmail que recibe en
   Resend. Usar `POST /api/clientes/login` o `POST /api/auth/login`.
2. Consultar `GET /api/servicios` y escoger un servicio disponible.
3. Consultar `GET /api/servicios/{idServicio}/disponibilidad?fecha=AAAA-MM-DD`.
   Elegir una opción que aparezca en esa respuesta. Si la opción requiere
   empleado o recurso, copiar también sus IDs.
4. Ejecutar `POST /api/reservas` con el JWT del cliente. Ejemplo que se observó
   disponible al preparar esta guía:

   ```json
   {
     "idServicio": 1,
     "fecha": "2026-11-25",
     "hora": "09:00"
   }
   ```

   Si la asignación del servicio requiere personal o recursos, el cuerpo también
   debe incluir `idEmpleado` y/o `idRecurso` con los IDs disponibles.
5. Resultado esperado: HTTP `201`, estado `CONFIRMADA` y un correo con asunto
   **Confirmación de tu reserva** al correo registrado en la cuenta cliente.

La reserva nace confirmada; no existe un endpoint aparte para confirmarla. El
remitente lo toma de `RESEND_FROM`; en la EC2 se verificó
`BookingNow <onboarding@resend.dev>`. Con el remitente de prueba de Resend, el
destinatario debe estar habilitado en esa cuenta de Resend.

### 3. Cancelar y recibir la notificación

1. Usar el `idReserva` que devolvió la creación.
2. Antes de la cita y con más de dos horas de anticipación, ejecutar
   `POST /api/reservas/{idReserva}/cancelacion` con el mismo JWT del cliente.
   Se puede enviar `{}` como cuerpo.
3. Resultado esperado: HTTP `200`, estado `CANCELADA` y correo con asunto
   **Actualización de tu reserva** al correo asociado a esa reserva.
4. Mostrar `GET /api/reservas/mias/historial`; la reserva cancelada debe aparecer.

No reutilizar una reserva que ya esté cancelada. Para repetir la demo, crear una
nueva reserva disponible. El proveedor y el administrador también pueden
cancelar; para esos roles la solicitud requiere `motivo`.

### 4. Generar un reporte y dejar un error visible

1. Iniciar sesión como **PROVEEDOR** o **ADMINISTRADOR**.
2. Ejecutar `POST /api/reportes`. Para reservas en PDF:

   ```json
   {
     "tipoReporte": "RESERVAS",
     "fechaDesde": "2026-11-01",
     "fechaHasta": "2026-12-06",
     "idNegocio": 4,
     "estado": "CANCELADA",
     "formato": "PDF"
   }
   ```

   Cambiar `formato` a `EXCEL` para obtener un `.xlsx`. También se puede usar
   `tipoReporte: "USO_SERVICIOS_RECURSOS"` y filtrar por negocio, servicio,
   recurso, estado y fechas. El reporte se genera en memoria y la respuesta lo
   descarga; la API no archiva el PDF/XLSX.
3. Para generar un error controlado que aparezca en observabilidad, repetir la
   solicitud con `fechaDesde` posterior a `fechaHasta`. Resultado esperado:
   HTTP `400` con el detalle de validación.

### 5. Presentar el centro de observabilidad

1. Abrir la raíz del backend `/` e iniciar sesión con el usuario administrador.
2. **Resumen:** mostrar volumen, tasa de éxito, errores, latencia y accesos
   fallidos.
3. **Tráfico API:** revisar solicitudes por intervalo, rutas consultadas y la
   tabla de eventos. Usar filtros de fecha, servicio, método y código HTTP.
4. **Errores:** filtrar por `4xx` o `5xx`, abrir el error recién generado y
   señalar fecha/hora, ruta, IP observada, Request ID, request y respuesta que
   quedaron capturados.
5. **Seguridad:** mostrar los intentos de inicio de sesión y sus resultados.
6. La ruta de auditoría también se puede verificar por API:
   `GET /api/admin/auditoria?limite=50`. Sin JWT debe responder `401`; con un
   JWT que no sea administrador debe responder `403`; con administrador,
   `200`.
7. Señalar el aviso de retención: los archivos se archivan por semestre; el
   panel consulta el semestre activo y hasta 500 eventos por archivo. Para
   histórico, descargar los JSONL archivados por SSH desde
   `/home/ubuntu/bookingnow-logs` o usar otra herramienta de consulta.

Las capturas de diagnóstico limitan el cuerpo a 16 KiB y redactan datos
sensibles. PDF/Excel y cuerpos binarios no se guardan en los logs. Cada archivo
activo rota al llegar a 10 MB y conserva una rotación anterior.

## Set de verificación funcional

| Prueba | Operación | Resultado esperado |
|---|---|---|
| Servicio vivo | `GET /health` | `200`, `UP` |
| Documentación | abrir `/swagger-ui.html` | Swagger UI carga |
| Catálogo | `GET /api/servicios?pagina=0&tamano=20` | página de servicios, acceso público |
| Detalle | `GET /api/servicios/{idServicio}` | datos públicos del servicio o `404` |
| Credenciales | `POST /api/auth/login` | JWT y cuenta/rol; claves incorrectas `401` |
| Acceso anónimo | `GET /api/reservas/mias/activas` | `401` |
| Disponibilidad | `GET /api/servicios/{idServicio}/disponibilidad?fecha=...` | horarios y IDs de recursos/personas disponibles |
| Crear reserva | `POST /api/reservas` como cliente | `201`, `CONFIRMADA`, notificación al correo registrado |
| Reservas activas | `GET /api/reservas/mias/activas` como cliente | solo las reservas propias futuras activas/confirmadas |
| Cancelación | `POST /api/reservas/{id}/cancelacion` | `200`, estado cancelado y notificación; bloquea estados no cancelables o menos de 2 h |
| Historial | `GET /api/reservas/mias/historial` | reservas propias pasadas/canceladas |
| PDF/XLSX | `POST /api/reportes` como proveedor/admin | archivo descargable y filtros aplicados |
| Reporte inválido | rango de fechas invertido | `400`, detalle de validación observable |
| Auditoría sin sesión | `GET /api/admin/auditoria` | `401` |
| Auditoría rol incorrecto | mismo endpoint como cliente/proveedor | `403` |
| Auditoría administrador | mismo endpoint como admin | `200`, peticiones e intentos de login recientes |
| Estado del dominio | pruebas de `EstadoReservaTest` y flujo de cancelar | transiciones válidas aceptadas, estados terminales rechazados |

## Inventario funcional actual del backend

| Área | Funcionalidad disponible | Acceso |
|---|---|---|
| Clientes | Registrar, iniciar sesión, consultar/eliminar la propia cuenta; reservas activas e historial | Registro/login público; operaciones propias con JWT cliente |
| Proveedores | Registrar, iniciar sesión, consultar/eliminar la propia cuenta | Registro/login público; operaciones propias con JWT proveedor |
| Negocios | Registrar varios negocios por proveedor, consultar los propios; administrador consulta el listado global | Proveedor y administrador según operación |
| Horarios | Configurar y consultar horarios de atención del negocio | Proveedor propietario |
| Servicios | Registrar servicio, asignar personal/recursos, listar en el negocio, catálogo con búsqueda/tipo/paginación y detalle público | Gestión de proveedor/administrador; catálogo público |
| Empleados y recursos | Registrar y listar por negocio | Proveedor propietario |
| Reservas | Consultar disponibilidad, crear confirmada, consultar activas/historial y cancelar con control de titularidad/rol y regla de dos horas | Cliente; proveedor/admin en cancelación |
| Notificaciones | Confirmación al reservar, notificación al cancelar y recordatorio programado 24 h antes | Email a la dirección guardada en la cuenta cliente; remitente Resend desde entorno |
| Reportes | Reservas o uso de servicios/recursos; filtros por fechas, negocio, servicio, recurso y estado; PDF/XLSX | Proveedor o administrador; proveedor limitado a sus negocios |
| Observabilidad | Peticiones, errores y accesos registrados en JSONL; panel con resumen, tráfico, errores y seguridad | Panel y API de auditoría para administrador |
| Documentación y salud | Swagger UI y endpoint de health | Público |

## Patrón State y límites

Antes de esta actualización la cancelación se decidía con condicionales sobre
cadenas. Ahora `domain/state/EstadoReserva` convierte el valor persistido en un
objeto de estado; `EstadoCancelableReserva` permite la transición y decide el
resultado según el actor; `EstadoNoCancelableReserva` rechaza una transición
posterior. Los estados persistidos y las respuestas HTTP no cambiaron. La regla
de dos horas, la titularidad y el motivo del proveedor/administrador continúan
siendo validaciones del servicio de aplicación.

Las pruebas ejecutadas en esta revisión: `.\mvnw.cmd verify` — 36 pruebas,
0 fallas, 0 errores. Incluyen la ruta de confirmación/cancelación con correo
simulado y las transiciones de estados; no mandan correos reales y usan H2, no
PostgreSQL. La entrega Resend ya fue comprobada manualmente por el equipo con
`onboarding@resend.dev`; un recordatorio automático requiere esperar el horario
de 24 horas o probar el servicio de manera controlada.

Fuera del alcance actual del backend están la administración/edición completa
de perfiles, negocio y servicio, los reintentos automáticos de correo y la
accesibilidad del frontend. Esas funciones no se deben presentar como terminadas
por esta demo.
