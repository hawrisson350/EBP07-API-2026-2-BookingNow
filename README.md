# BookingNow API

Java 21, Spring Boot 4.1.1, PostgreSQL y arquitectura hexagonal.
Infraestructura preparada para Render + Supabase; aun sin despliegue remoto.
Ver [la guia de infraestructura](docs/DEPLOYMENT.md).

## Modelo provisional

`Usuario` fue sustituido por dos modelos independientes, sin herencia:

| Modelo | Campos |
|---|---|
| Cliente | idCliente, correo, nombreUsuario, contrasenaHash |
| Proveedor | idProveedor, correo, nombreUsuario, contrasenaHash, razonSocial, nit |

Los IDs son Long en Java y bigint en PostgreSQL. La contrasena se recibe al
registrar o iniciar sesion; solo se almacena su hash BCrypt y nunca se devuelve.
Nombre de usuario: 3-50 letras ASCII, numeros, puntos, guiones o guiones bajos;
se normaliza a minusculas y debe ser unico **por tipo de cuenta**. Un mismo nombre
puede existir como cliente y proveedor; la ruta de login distingue el tipo.
Contrasena: minimo 8 caracteres, maximo 72 bytes UTF-8, sin recortar ni cambiar.
Correo obligatorio con formato basico. Razon social y NIT son obligatorios para
proveedores; las reglas fiscales del NIT quedan pendientes del modelo definitivo.
Estas reglas iniciales pueden ajustarse con los requisitos de clase.

Reserva, Negocio, Servicio y Multimedia todavia no estan implementados.

## Estructura hexagonal

```text
domain/model/                   Cliente y Proveedor, sin JPA
application/port/in/cliente/    Casos de uso y comando de registro de clientes
application/port/in/proveedor/  Casos de uso y comando de registro de proveedores
application/port/in/auth/       Credenciales compartidas para iniciar sesion
application/port/out/           Repositorios y ContrasenaPort
application/service/            Casos de uso y validaciones
infrastructure/adapter/in/rest/  Controladores, respuestas sin credenciales
infrastructure/adapter/out/     Persistencia JPA y BCrypt
infrastructure/config/          Sesiones y autorizacion con Spring Security
```

Cada servicio implementa los puertos de entrada y utiliza los puertos de salida.
Los adaptadores convierten dominio/entidad JPA. Las sesiones y HTTP se gestionan
fuera del dominio. El listado se conserva como caso de uso interno; su acceso
HTTP esta bloqueado hasta definir un rol administrador.

## Compilar sin Docker en Windows

Abrir PowerShell en la raiz del proyecto (donde esta `pom.xml`). Requisitos:

- JDK 21 instalado y `JAVA_HOME` apuntando a su carpeta de instalacion.
- Conexion a Internet para descargar Maven y dependencias la primera vez.

No necesitas instalar Maven: el proyecto incluye Maven Wrapper. Comprobar Java:

```powershell
java -version
javac -version
.\mvnw.cmd -version
```

Solo compilar el codigo, sin ejecutar pruebas ni generar el JAR:

```powershell
.\mvnw.cmd clean compile
```

Compilar, ejecutar las pruebas y generar el JAR ejecutable:

```powershell
.\mvnw.cmd clean verify
```

El archivo generado es `target/bookingnow-0.0.1-SNAPSHOT.jar`.
**Para compilar y ejecutar las pruebas no necesitas Docker ni PostgreSQL:**
las pruebas usan H2 en memoria. Para iniciar la API normalmente si necesitas
una conexion a PostgreSQL.

## Ejecutar localmente sin Docker

### Demo local sin PostgreSQL (servidor de desarrollo)

Para abrir Swagger y probar la API antes de configurar la BD, ejecutar en
PowerShell desde la raiz del proyecto:

```powershell
.\mvnw.cmd test-compile spring-boot:run "-Dspring-boot.run.useTestClasspath=true" "-Dspring-boot.run.additional-classpath-elements=target/test-classes" "-Dspring-boot.run.profiles=test" "-Dspring-boot.run.arguments=--server.address=127.0.0.1 --server.port=8080"
```

Mantener esa terminal abierta y visitar `http://localhost:8080/swagger-ui.html`.
Este comando usa H2 en memoria mediante el perfil de pruebas: los datos se
pierden al detener el servidor con `Ctrl+C`. Solo escucha en la maquina local.
La configuracion normal y la de Render siguen usando PostgreSQL.

### Servidor con PostgreSQL

Con PostgreSQL instalado y ejecutandose, crear primero la base `bookingnow`
(por ejemplo, desde pgAdmin con `CREATE DATABASE bookingnow;`). Configurar las
credenciales reales en la misma terminal de PowerShell:

```powershell
$env:DB_URL = 'jdbc:postgresql://localhost:5432/bookingnow'
$env:DB_USERNAME = 'postgres'
$env:DB_PASSWORD = 'tu-clave-local'
java -jar .\target\bookingnow-0.0.1-SNAPSHOT.jar
```

Como alternativa al JAR, ejecutar directamente con Maven:

```powershell
.\mvnw.cmd spring-boot:run
```

Usar una sola de las dos opciones de arranque a la vez. La API queda en
`http://localhost:8080` y Swagger en `http://localhost:8080/swagger-ui.html`.
Para detenerla, presionar `Ctrl+C`. Si el puerto esta ocupado, definir
`$env:PORT = '8081'` antes de arrancar y usar ese puerto en las URLs.

Spring Boot no lee `.env` automaticamente: para estos comandos se usan las
variables `$env:...`. Para conectar con Supabase en lugar de PostgreSQL local,
seguir [la guia de infraestructura](docs/DEPLOYMENT.md).

El perfil local usa `ddl-auto=update`. El perfil `cloud` usa `validate` y requiere
las tablas del contrato descrito en docs/DEPLOYMENT.md. No se borran tablas remotas.

## Swagger UI

Con la API iniciada y la BD disponible, abrir:

- Interfaz: `http://localhost:8080/swagger-ui.html`
- Documento OpenAPI: `http://localhost:8080/v3/api-docs`

En Render, usar las mismas rutas sobre la URL HTTPS de la API.
La documentacion es publica; los permisos de los endpoints se mantienen.

Para probar desde Swagger UI:

1. Ejecutar `GET /api/auth/csrf` con **Try it out > Execute**.
2. Copiar el campo `token` de la respuesta.
3. Pulsar **Authorize**, pegarlo en `csrfToken` y confirmar.
4. Registrar una cuenta con `POST /api/clientes` o `POST /api/proveedores`.
   Completar los campos con valores validos segun las reglas anteriores.
5. Ejecutar el `/login` correspondiente con `nombreUsuario` y `contrasena`.
6. Consultar o eliminar el propio ID: el navegador envia la cookie de sesion.
7. Para salir, ejecutar `POST /api/auth/logout`.

Despues del logout o de expirar la sesion, repetir los pasos 1-3. El token CSRF
no reemplaza el login. Los listados globales bloqueados no aparecen en Swagger.
La integracion usa [springdoc-openapi](https://springdoc.org/) 3.1.1 para Spring Boot 4.

## Registro e inicio de sesion

Las rutas de cliente y proveedor son independientes. No se genera JWT: el login
crea una sesion con cookie HttpOnly, que vence tras 30 minutos de inactividad.
Las operaciones POST y DELETE requieren un token CSRF y la cookie asociada.

| Metodo | Ruta | Resultado |
|---|---|---|
| GET | `/health` | Estado de API y BD, publico |
| GET | `/api/auth/csrf` | Token y nombre del header CSRF, publico |
| POST | `/api/clientes` | Registro, 201 |
| POST | `/api/proveedores` | Registro, 201 |
| POST | `/api/clientes/login` | Login de cliente, 200 y cookie de sesion |
| POST | `/api/proveedores/login` | Login de proveedor, 200 y cookie de sesion |
| GET | `/api/clientes/{id}` o `/api/proveedores/{id}` | Solo el titular, 200 |
| DELETE | `/api/clientes/{id}` o `/api/proveedores/{id}` | Solo el titular, 204 y cierre de su sesion actual |
| POST | `/api/auth/logout` | Cierra la sesion, 204 |

Datos invalidos: 400; credenciales incorrectas o acceso sin sesion: 401;
CSRF ausente/acceso a otra cuenta: 403; nombre duplicado: 409.
Las rutas antiguas `/api/usuarios` ya no existen.

Ejemplo completo en PowerShell (conserva cookies):

```powershell
$base = 'http://localhost:8080'
$csrf = Invoke-RestMethod "$base/api/auth/csrf" -SessionVariable sesion
$headers = @{}
$headers[$csrf.headerName] = $csrf.token

$registro = @{ correo='ana@example.com'; nombreUsuario='ana'; contrasena='ClaveDemo123!' } | ConvertTo-Json
Invoke-RestMethod "$base/api/clientes" -Method Post -WebSession $sesion -Headers $headers -ContentType 'application/json' -Body $registro

$login = @{ nombreUsuario='ana'; contrasena='ClaveDemo123!' } | ConvertTo-Json
$cliente = Invoke-RestMethod "$base/api/clientes/login" -Method Post -WebSession $sesion -Headers $headers -ContentType 'application/json' -Body $login
Invoke-RestMethod "$base/api/clientes/$($cliente.idCliente)" -WebSession $sesion
Invoke-RestMethod "$base/api/auth/logout" -Method Post -WebSession $sesion -Headers $headers
```

Para proveedor, usar `/api/proveedores` y agregar al registro `razonSocial` y `nit`:

```json
{"correo":"negocio@example.com","nombreUsuario":"negocio","contrasena":"ClaveDemo123!","razonSocial":"Negocio Demo","nit":"900123456-7"}
```

El frontend debera conservar cookies y enviar el header CSRF. La integracion con
un frontend de otro origen (CORS y politica de cookies) queda por definir cuando
se conozca su dominio. No hay recuperacion de contrasena ni limitacion de intentos
por ahora. Las sesiones no sobreviven a reinicios de Render.

## Verificacion

```powershell
.\mvnw.cmd clean verify
```

Las pruebas usan H2 solo para pruebas: registro, hash persistido, login de ambos
tipos, validaciones, duplicados, CSRF, permisos, logout, eliminacion, health y
validacion del esquema cloud. No sustituyen probar PostgreSQL real.

Docker local con BD: `docker compose up --build -d` (definir DB_PASSWORD).
Docker con Supabase externo: copiar `.env.example` a `.env`, completar sus valores
y ejecutar `docker compose -f compose.cloud.yaml up --build -d`.
Spring Boot por si solo no carga `.env`. Ver la guia para HTTPS y perfil cloud.
