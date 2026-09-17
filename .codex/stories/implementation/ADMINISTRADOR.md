# Usuario administrador

- Fecha: 2026-09-17
- Estado: backend implementado y verificado localmente.

## Decisión

Se creó la entidad independiente `Usuario`; no sustituye ni hereda de Cliente o
Proveedor. Por ahora su único valor válido de `rol` es `ADMINISTRADOR`.
No existe endpoint público para crearlo: [initial-admin.sql](../../../docs/db/initial-admin.sql)
lo inicializa desde SQL con estado ACTIVA y contraseña BCrypt.

## Flujo

`POST /api/auth/login` identifica el tipo `ADMINISTRADOR`, emite un JWT con
`roles: ["ADMINISTRADOR"]` y habilita `GET /api/clientes` y
`GET /api/proveedores`. El JWT se invalida si el usuario no existe o deja de
estar ACTIVA.

## Persistencia

`usuarios` se incorpora al contrato de BD y al perfil cloud. La migración
actualiza el CHECK de `correos_registrados.tipo` para incluir ADMINISTRADOR.
Se mantiene RLS habilitado sin `FORCE`, porque la API JDBC usa el propietario.

## Verificación

La prueba `usuarioAdministradorIniciaSesionYConsultaListadosGlobales` crea un
administrador en H2, inicia sesión y obtiene ambos listados con HTTP 200.
Ejecución aislada: Maven verify, 19 pruebas, 0 fallos, 0 errores, 0 omitidas.
Pendiente: ejecutar `initial-admin.sql` y repetir ese flujo en Supabase.
