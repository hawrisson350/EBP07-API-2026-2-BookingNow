# Contexto de BookingNow

Fecha de referencia: 2026-10-17. Verificar el codigo antes de tomar decisiones.
Fuentes: README.md, pom.xml, src/ y conversaciones/requisitos del equipo.

## Decisiones actuales

- API Java 21 / Spring Boot 4.1.1, Maven Wrapper.
- Arquitectura hexagonal basada en el ejemplo de clase.
- Puertos de entrada agrupados por entidad en `application/port/in`.
- Dominio independiente de HTTP/JPA; controladores y persistencia en adaptadores.
- PostgreSQL en Supabase y API desplegada en una instancia EC2 de AWS.
  URL actual: http://3.136.161.165:8080. Confirmar disponibilidad antes de usarla.
- H2 solo en pruebas y demo local; no reemplaza la BD prevista.
- Cliente, Proveedor y Usuario son modelos separados, sin herencia. Usuario se
  incorporó el 2026-09-17 exclusivamente para el rol ADMINISTRADOR.
- Autenticacion actual: JWT Bearer, BCrypt para contrasenas. JWT_SECRET fuera de Git.
- El perfil cloud valida tablas; las migraciones corresponden al repositorio de BD.

## Requisitos recibidos del Sprint 1

El 2026-09-17 se incorporó `workspace/Recuento HU.docx` a las cinco fichas de
`stories/detail-user-stories`. Ver `stories/analysis/SPRINT-1-revision-inicial.md`.

- El registro mantiene nombreUsuario; el login debe usar correo y contraseña.
- Se exige contraseña con letra, número y carácter especial (mínimo 8 caracteres).
- Proveedor requiere razón social de mínimo 3 caracteres, NIT con formato
  de 9 dígitos-guion-1 dígito y unicidad de correo, NIT y razón social.
- Login incluye cuentas inactivas/bloqueadas y resultado en máximo tres segundos.
- Negocio incluye modalidad virtual y galería multimedia: no excluir Multimedia
  del sprint por la lectura previa de solo los títulos.
- Desde 2026-09-21 un proveedor puede registrar varios negocios. `GET /api/negocios/mio`
  devuelve la lista de negocios del proveedor autenticado.
- Desde 2026-09-21 las fotos y la galería se reciben como data URLs Base64 en JSON;
  no se cargan archivos y cada elemento está limitado a 5 MiB sin codificar.
- Backend de las cinco HU implementado; 18 pruebas aprobadas con H2. Ver
  `stories/implementation/SPRINT-1.md` y `stories/testing/SPRINT-1-testing.md`.
- Login común por correo en `/api/auth/login`; roles CLIENTE y PROVEEDOR.
- Decisiones técnicas provisionales: correo único entre roles, cuentas nuevas ACTIVA,
  razón social normalizada, fotos y multimedia como data URLs Base64 en JSON, duración entera y precio
  con dos decimales. No son requisitos adicionales del Word.
- Pendientes de aceptación: frontend, PostgreSQL real y tiempos del despliegue.
- Registro devuelve `mensaje` y `datos`; login común devuelve `cuenta.id` y `cuenta.rol`.
- BD existente necesita migrar datos y rellenar `correos_registrados` antes del login.
- Sprint 2 incorpora código backend para horarios de atención, búsqueda/detalle de servicios, recursos, empleados, asignaciones y reservas. Ver `.codex/stories/implementation/SPRINT-2.md` para alcance y brechas.
- El contrato SQL de Sprint 2 está en `docs/db/sprint-2-schema.sql`; todavía no se ha aplicado/verificado en Supabase.
- La verificación Sprint 2 ejecutó `mvnw verify`: 26 pruebas pasaron con H2; el detalle por criterio está en `.codex/stories/testing/`. PostgreSQL real, frontend y correo siguen pendientes. H2 no valida concurrencia ni compatibilidad final con Supabase.
- El 2026-11-21 se recuperó desde el chat «Inventario HU Sprint 3» `Inventario-HU-Sprint-3.xlsx`, conservado localmente en `workspace/` (no publicar el original). Se añadió la hoja Sprint 3 a `docs/reportes/reporte-hu.xlsx` y se generaron diez fichas de detalle y diez análisis iniciales en `.codex/stories/`. Fuente enlazada por PBI: Azure DevOps IDs 33, 31, 35, 36, 37, 109, 87, 94, 140 y 141.
- Sprint 3 en desarrollo: se implementaron consultas propias/historial, cancelación con regla de dos horas, generación de reportes PDF/XLSX, emails de confirmación/cancelación con Resend y scheduler para recordatorios 24h antes. Compilación local satisfactoria; no hay pruebas de comportamiento ni entrega real de correo verificada. La clave se configura localmente fuera de Git; el SQL extendido no se aplicó en PostgreSQL. Ver `.codex/stories/implementation/SPRINT-3.md` y `.codex/stories/analysis/SPRINT-3-alcance-y-brechas.md`.
- `docs/db/initial-admin.sql` documenta el usuario administrador de desarrollo; verificar contra la instancia antes de operar.

## Verificacion

Usar `.\mvnw.cmd verify` en Windows. Hay antecedentes de interferencia del IDE
sobre `target`; si reaparecen clases inconsistentes, verificar en una copia
 temporal aislada y registrar la ubicacion y el motivo. No afirmar que se
probo PostgreSQL si se ejecuto solo H2.

## Registro de decisiones futuras

Agregar fecha, decision y enlace a la HU o fuente. Las dudas permanecen como
pendientes hasta que el equipo las resuelva; no convertir suposiciones en reglas.

- 2026-11-22: proveedor de email transaccional elegido: Resend. El adaptador usa `RESEND_API_KEY` y `RESEND_FROM`, mantenidos fuera de Git. La clave compartida en la conversación debe revocarse y reemplazarse antes de probar envíos; no persistir secretos en memoria o documentación.
- 2026-11-22: se añadió bitácora local JSONL de peticiones y de intentos de login, consulta solo para ADMINISTRADOR en `/api/admin/auditoria` y pantalla incluida en el React. En EC2 la ruta persistente es `/home/ubuntu/bookingnow-logs`; archivos rotan a 10 MB conservando una copia. Se valida IP en red host; no se registran cuerpos completos ni contraseñas/tokens. Despliegue actual responde en `http://3.136.161.165:8080`; falta iniciar sesión real de administrador desde navegador para revisar la pantalla.
