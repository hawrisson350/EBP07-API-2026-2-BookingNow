# Contexto de BookingNow

Fecha de referencia: 2026-09-17. Verificar el codigo antes de tomar decisiones.
Fuentes: README.md, pom.xml, src/ y conversaciones/requisitos del equipo.

## Decisiones actuales

- API Java 21 / Spring Boot 4.1.1, Maven Wrapper.
- Arquitectura hexagonal basada en el ejemplo de clase.
- Puertos de entrada agrupados por entidad en `application/port/in`.
- Dominio independiente de HTTP/JPA; controladores y persistencia en adaptadores.
- PostgreSQL previsto en Supabase y API en Render; infraestructura preparada.
  No afirmar que existe un despliegue remoto sin comprobarlo.
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
- El usuario confirmó un único negocio por proveedor durante este sprint (2026-09-17).
- Backend de las cinco HU implementado; 18 pruebas aprobadas con H2. Ver
  `stories/implementation/SPRINT-1.md` y `stories/testing/SPRINT-1-testing.md`.
- Login común por correo en `/api/auth/login`; roles CLIENTE y PROVEEDOR.
- Decisiones técnicas provisionales: correo único entre roles, cuentas nuevas ACTIVA,
  razón social normalizada, multimedia mediante URLs HTTPS, duración entera y precio
  con dos decimales. No son requisitos adicionales del Word.
- Pendientes de aceptación: frontend, PostgreSQL real y tiempos del despliegue.
- Registro devuelve `mensaje` y `datos`; login común devuelve `cuenta.id` y `cuenta.rol`.
- BD existente necesita migrar datos y rellenar `correos_registrados` antes del login.
- No existe módulo de reservas ni carga de archivos multimedia.
- `docs/db/initial-admin.sql` crea el primer administrador; no hay registro
  público de administradores. Falta aplicarlo y probarlo en Supabase.

## Verificacion

Usar `.\mvnw.cmd verify` en Windows. Hay antecedentes de interferencia del IDE
sobre `target`; si reaparecen clases inconsistentes, verificar en una copia
 temporal aislada y registrar la ubicacion y el motivo. No afirmar que se
probo PostgreSQL si se ejecuto solo H2.

## Registro de decisiones futuras

Agregar fecha, decision y enlace a la HU o fuente. Las dudas permanecen como
pendientes hasta que el equipo las resuelva; no convertir suposiciones en reglas.
