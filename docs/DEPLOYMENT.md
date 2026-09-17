# Render + Supabase

La API se despliega con `render.yaml`. Supabase aloja PostgreSQL por separado.
El perfil `cloud` valida el esquema; no crea ni modifica tablas. Las migraciones
deben mantenerse en el repositorio de BD del equipo.

## 1. Preparar Supabase

Crear un proyecto Free llamado BookingNow y guardar su contraseña de base de
datos. Elegir una región cercana a la de Render. No se requieren API keys de
Supabase para JDBC.

El contrato del Sprint 1 está en [db/sprint-1-schema.sql](db/sprint-1-schema.sql):
cuentas, usuario administrador, registro global de correos, negocios, galería y servicios. Es una base
para una **BD nueva**, no una migración de instalaciones existentes. Coordinar
su aplicación con el repositorio de BD antes de iniciar el perfil cloud.

Si ya existen cuentas, preparar una migración que resuelva correos/NIT/razones
sociales duplicadas, complete `estado` y `razon_social_normalizada`, y rellene
`correos_registrados` con el tipo correcto. Normalizar correos a minúsculas y razón
social como la API (recortar, colapsar espacios y pasar a minúsculas). Hacerlo
antes de habilitar el nuevo login: este usa el registro global para identificar
el rol. `CREATE TABLE IF NOT EXISTS` no actualiza columnas ni migra datos.
El esquema inicial y las pruebas de registro/login de Cliente y Proveedor ya se
ejecutaron contra el proyecto Supabase del equipo. Para añadir `Usuario` y el
administrador, ejecutar ahora [db/initial-admin.sql](db/initial-admin.sql) en SQL
Editor y reiniciar la API. La fixture también se valida en H2; falta repetir una
prueba PostgreSQL del flujo de administrador después de aplicar esa migración.

El esquema `bookingnow` es para JDBC: no agregarlo a los esquemas expuestos por
la Data API de Supabase. Las validaciones de correo, nombre de usuario y contrasena estan en la API.

Render Free no dispone de la ruta IPv6 requerida por la conexión directa de
Supabase. Por eso este proyecto usa **Connect > Session pooler**, que ofrece
acceso IPv4 por el puerto **5432**. El `render.yaml` ya contiene el host y
usuario del pooler; solo la contraseña sigue siendo una variable de Render.
En este entorno, Render corta la negociación TLS con el pooler antes de autenticar. Por eso el Blueprint usa `sslmode=disable`; Supabase debe conservar desactivada la opción **Enforce SSL on incoming connections** (Database > Settings > SSL Configuration).

## 2. Publicar la API en Render

Los cambios, incluido `render.yaml`, deben estar en la rama de GitHub que se
desplegará: `https://github.com/hawrisson350/EBP07-API-2026-2-BookingNow`.

1. En Render, crear un **Blueprint** y conectar ese repositorio y la rama elegida.
2. Revisar que `bookingnow-api` use el plan **Free**.
3. Completar las variables solicitadas:

| Variable | Valor |
|---|---|
| `DB_URL` | Lo define `render.yaml` con el Session pooler de Supabase |
| `DB_USERNAME` | Lo define `render.yaml` con el usuario del pooler |
| `DB_PASSWORD` | Contraseña de PostgreSQL, no API key |
| `JWT_SECRET` | Al menos 32 bytes aleatorios en Base64; generar como indica README.md |
| `CORS_ALLOWED_ORIGINS` | URL HTTPS del frontend, por ejemplo `https://bookingnow-front.onrender.com` |

La contraseña se guarda por separado, sin incluirla en la URL. `sslmode=disable` se usa solo porque Render Free no completó el handshake TLS con este pooler; no activar **Enforce SSL** en Supabase mientras exista esa limitación.
El Blueprint configura `SPRING_PROFILES_ACTIVE=cloud`, health check `/health`
y un heap máximo de Java de 256 MB. La JVM también consume memoria fuera del heap:
hay que comprobar el consumo total dentro de los 512 MB del plan gratuito.

El Dockerfile compila y ejecuta las pruebas antes de producir la imagen con
Java 21. No requiere subir `target` ni ejecutar Docker Compose en Render.
El puerto se toma de `PORT`, con 8080 como valor local por defecto.

## Logs iniciales para diagnóstico

Render conserva la salida estándar de la API. Cada llamada a `/api/*` y
`/health` deja `requestId`, método, ruta, estado y duración, sin registrar
contraseñas, tokens, hashes ni cuerpos JSON. La API responde el mismo valor en
`X-Request-Id`; el frontend puede enviarlo y luego buscarlo en Render > Logs.

El login del administrador también registra un resultado interno seguro:
`usuario_no_encontrado`, `contrasena_incorrecta`, `cuenta_no_habilitada` o
`exitoso`. Si el despliegue no arranca, buscar `application_ready`; esa línea
informa el perfil y orígenes CORS, sin exponer URL ni credenciales de la BD.

## 3. Verificar el despliegue

Con la URL real asignada por Render:

```powershell
$apiUrl = 'https://NOMBRE-ASIGNADO.onrender.com'
Invoke-RestMethod "$apiUrl/health"
Invoke-RestMethod "$apiUrl/v3/api-docs"
```

Health debe devolver `status: UP`; también comprueba la conexión a la BD.
Si falta la tabla, Hibernate falla al arrancar: aplicar primero la migración.
Si aparece `Tenant or user not found`, revisar host y usuario del Session pooler.
La API usa JWT Bearer. Ver el flujo de registro/login en README.md.
Usar datos ficticios mientras se define el modelo final.

## Prueba local con Supabase

Para este proyecto académico, `.env` ya está incluido en el repositorio y Spring
Boot lo carga automáticamente. Desde la raíz, ejecutar:

```powershell
.\mvnw.cmd spring-boot:run
```

También puedes usar `docker compose -f compose.cloud.yaml up --build -d`. Este
Compose ejecuta solo la API, con límite de 512 MB. `compose.yaml` queda como
alternativa local con PostgreSQL incluido.

## Referencias

- [Conexiones PostgreSQL en Supabase](https://supabase.com/docs/guides/database/connecting-to-postgres)
- [Blueprint de Render](https://render.com/docs/blueprint-spec)
- [Límites gratuitos de Render](https://render.com/docs/free)

Render Free se suspende tras inactividad y tiene cuotas mensuales. Antes de una
entrega, comprobar que tanto la API como el proyecto Supabase estén activos.

## Modelo de cuentas

Cliente y Proveedor son cuentas independientes. La tabla `usuarios` contiene,
por ahora, solo cuentas con rol `ADMINISTRADOR`. El perfil cloud requiere las
tablas del contrato del Sprint 1, incluido el administrador creado por
`db/initial-admin.sql`.

Los JWT siguen validos tras reinicios si JWT_SECRET no cambia y la cuenta existe y está ACTIVA.
Los tokens vencen a los 30 minutos por defecto; el frontend debe descartarlos al
cerrar sesion. No usar en Render la clave incluida en el perfil test.
