# Despliegue de BookingNow en AWS

## Arquitectura actual

- **API:** una instancia EC2 de AWS, accesible en `http://3.136.161.165:8080`.
- **Frontend React del equipo:** publicado por separado en Vercel.
- **Panel de logs:** página HTML pequeña incluida en este repositorio backend, disponible en la raíz de la URL de la API (`/`).
- **Base de datos:** PostgreSQL en Supabase; la EC2 se conecta por JDBC.
- **Correo:** Resend. `RESEND_API_KEY` y `RESEND_FROM` se configuran solo en el entorno del servidor.
- **Autenticación:** JWT Bearer. Las rutas de auditoría requieren el rol `ADMINISTRADOR`.

El perfil cloud usa el esquema `bookingnow` y valida su estructura; no aplica
migraciones automáticamente. Antes de desplegar cambios de base de datos,
coordinar y aplicar el SQL del repositorio de BD. Para el Sprint 3, revisar
`docs/db/sprint-2-schema.sql`, incluido el campo de recordatorio.

## Despliegue en EC2

El despliegue actual usa un contenedor Docker basado en Java 21. El contenedor se
ejecuta con reinicio automático y red del host para que Spring registre la IP
de origen que llega a EC2. El proceso corre con UID `10001`, límite de memoria
de 512 MB y puerto 8080.

La PEM se conserva localmente y no forma parte del repositorio. Para entrar,
usar SSH con el usuario `ubuntu` y la IP vigente de la instancia. No pegar ni
subir la PEM al proyecto.

Al preparar un despliegue nuevo:

1. Compilar el backend con Java 21: `./mvnw -DskipTests package` (o `mvnw.cmd`
   en Windows). Esta instrucción compila, no ejecuta pruebas.
2. Copiar el JAR a la EC2 sin modificar su `.env` remoto.
3. Ejecutar el contenedor con perfil `cloud`, el `.env` que ya existe en EC2 y
   `BOOKINGNOW_AUDIT_DIRECTORY=/app/logs`.
4. Montar `/home/ubuntu/bookingnow-logs` del host en `/app/logs` del contenedor,
   asegurar escritura para UID `10001`, y mantener el reinicio automático.
5. Verificar `/health`, la página `/` y que `/api/admin/auditoria` devuelve 401
   cuando se consulta sin token.

El servicio actual de Docker se llama `bookingnow-api`; su contenedor anterior
se mantiene detenido como opción de recuperación. La dirección pública puede
cambiar si AWS reasigna la IP. Confirmarla en EC2 antes de compartirla.

## Logs y auditoría

La aplicación mantiene dos archivos JSON Lines en el volumen persistente:

| Archivo | Contenido |
|---|---|
| `requests.jsonl` | Fecha, actor cuando está autenticado, ruta, método, estado, duración, IP, agente de usuario y `requestId` de las solicitudes API y `/health`. |
| `login-attempts.jsonl` | Resultado, rol, correo enmascarado, IP observada, agente de usuario y ruta de los intentos procesados por los endpoints de login. |

Cada archivo rota al llegar a 10 MB; se conserva una rotación anterior. El
backend no registra cuerpos HTTP, contraseñas, tokens ni imágenes Base64. La IP
reenviada en `X-Forwarded-For` se guarda como referencia, no como origen
verificado.

El **Centro de logs** es una página HTML sencilla servida por el mismo backend
en `/`; no forma parte del frontend React del equipo. El administrador inicia
sesión ahí y la página consulta `GET /api/admin/auditoria` (hasta 200 entradas
recientes por archivo). La consulta está protegida por el rol administrador.
Para revisar directamente por SSH, consultar los JSONL dentro de
`/home/ubuntu/bookingnow-logs`.

Los archivos están en el disco de la misma EC2: sirven para la demostración y
el diagnóstico, pero no son una copia de seguridad independiente. La instancia
actual estuvo al 99% de uso de disco durante este despliegue. Revisar espacio
antes de nuevos despliegues; la rotación limita cada archivo, no el uso total de
Docker ni del sistema.

## Configuración local

Copiar `.env.example` a `.env` y completar los datos propios de PostgreSQL,
`JWT_SECRET` y, si se van a probar correos, `RESEND_API_KEY` y `RESEND_FROM`.
`.env` está ignorado por Git. No reutilizar claves del historial ni guardar
credenciales en README, código o capturas.

```powershell
Copy-Item .env.example .env
.\mvnw.cmd spring-boot:run
```

Para más información de endpoints y formato para el equipo de React, consultar
[README.md](../README.md).
