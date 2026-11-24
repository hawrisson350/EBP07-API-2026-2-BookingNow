# Despliegue de BookingNow en AWS

## Arquitectura actual

- **API:** una instancia EC2 de AWS, accesible en `http://ec2-3-136-161-165.us-east-2.compute.amazonaws.com:8080` (también responde por la IPv4 pública actual).
- **Frontend React del equipo:** publicado por separado en Vercel.
- **Panel de logs:** página HTML pequeña de este repositorio, disponible en `/`. En EC2 se sirve desde un archivo separado del JAR.
- **Base de datos:** PostgreSQL en Supabase; la EC2 se conecta por JDBC.
- **Correo:** Resend por defecto. También está disponible Gmail SMTP con `BOOKINGNOW_EMAIL_PROVIDER=gmail`, una contraseña de aplicación y STARTTLS.
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

### Actualizar solo el panel

El HTML del panel pesa unos 48 KB. La EC2 ya tiene el directorio
`/home/ubuntu/bookingnow-dashboard` montado como solo lectura en `/app/dashboard`,
y `BOOKINGNOW_DASHBOARD_FILE=/app/dashboard/index.html`. Para cambios visuales,
basta reemplazar ese HTML por SSH: no hace falta reconstruir ni transferir el JAR
de aproximadamente 90 MB, y no hace falta reiniciar el backend. El JAR conserva
el HTML integrado como respaldo para entornos sin ese montaje.

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

El servicio actual de Docker se llama `bookingnow-api`; el contenedor anterior
se mantiene detenido como opción de recuperación. La dirección pública puede
cambiar si AWS reasigna la IP. Confirmarla en EC2 antes de compartirla.

## Logs y auditoría

La aplicación mantiene dos archivos JSON Lines en el volumen persistente:

| Archivo | Contenido |
|---|---|
| `requests.jsonl` | Fecha, actor cuando está autenticado, ruta, método, estado, duración, IP, agente de usuario y `requestId` de las solicitudes API y `/health`. También guarda el JSON de entrada de `POST /api/reportes` y respuestas de error de tipo texto/JSON. |
| `login-attempts.jsonl` | Resultado, rol, correo enmascarado, IP observada, agente de usuario y ruta de los intentos procesados por los endpoints de login. |

Cada archivo activo rota al llegar a 10 MB; se conserva una rotación anterior.
Al cambiar el semestre (enero o julio), el siguiente evento archiva el archivo
activo y su rotación con nombres como `requests-2026-S2.jsonl`. El panel solo
consulta los archivos del semestre activo y hasta 500 eventos por archivo.
Para periodos anteriores, descarga los JSONL archivados por SSH desde
`/home/ubuntu/bookingnow-logs` o consulta la herramienta histórica que use el
equipo. Las
capturas de diagnóstico tienen un máximo de 16 KiB. Se omiten respuestas
binarias (por ejemplo, PDF y Excel) y se redactan campos sensibles como
contraseñas, tokens, secretos e imágenes Base64. Para excepciones no controladas
se guarda el tipo y un mensaje acotado, porque el servidor puede generar el
JSON 500 después de que el filtro de auditoría termina. La IP reenviada en
`X-Forwarded-For` se guarda como referencia, no como origen verificado.

El **Centro de logs** es una página HTML sencilla servida por el mismo backend
en `/`; no forma parte del frontend React del equipo. El administrador inicia
sesión ahí y la página consulta `GET /api/admin/auditoria` (hasta 500 entradas
recientes por archivo). Al seleccionar una fila se muestran fecha, hora, ruta,
IP observada, Request ID, request del reporte o detalle del error. La consulta
está protegida por el rol administrador.
Para revisar directamente por SSH, consultar los JSONL dentro de
`/home/ubuntu/bookingnow-logs`.

El aviso del panel explica que los logs se archivan cada seis meses y que para
consultar periodos anteriores hay que usar una herramienta de consulta o
descargar los JSONL archivados. El chequeo de salud no prueba SMTP: se desactiva
su indicador de correo para que una cuenta de envío aún no configurada no marque
como caída toda la API.

Los archivos están en el disco de la misma EC2: sirven para la demostración y
el diagnóstico, pero no son una copia de seguridad independiente. La instancia
actual estuvo al 99% de uso de disco durante este despliegue. Revisar espacio
antes de nuevos despliegues; la rotación limita cada archivo, no el uso total de
Docker ni del sistema.

## Configuración local

Copiar `.env.example` a `.env` y completar los datos propios de PostgreSQL y
`JWT_SECRET`. Para correo, dejar Resend o cambiar `BOOKINGNOW_EMAIL_PROVIDER`
a `gmail` e indicar `MAIL_USERNAME`, `MAIL_APP_PASSWORD` y `MAIL_FROM`.
La cuenta UdeA debe permitir contraseñas de aplicación. No usar la contraseña
normal de Google ni guardar secretos en Git.
`.env` está ignorado por Git. No reutilizar claves del historial ni guardar
credenciales en README, código o capturas.

```powershell
Copy-Item .env.example .env
.\mvnw.cmd spring-boot:run
```

Para más información de endpoints y formato para el equipo de React, consultar
[README.md](../README.md).
