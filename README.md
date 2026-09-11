# BookingNow API

Proyecto Spring Boot con Java 21, Maven Wrapper y Spring Boot Actuator.

## Requisitos

- JDK 21 y `JAVA_HOME` apuntando a su directorio de instalación.
- Conexión a Internet para descargar las dependencias en la primera ejecución.

## Ejecutar

En PowerShell:

```powershell
.\mvnw.cmd spring-boot:run
```

En Linux o macOS: `./mvnw spring-boot:run`.

## Health

```http
GET http://localhost:8080/health
```

Respuesta con el servicio disponible: HTTP 200.

```json
{"status":"UP"}
```

El endpoint utiliza Actuator y no expone detalles internos. Si el estado es
`DOWN` u `OUT_OF_SERVICE`, responde HTTP 503.

## Verificar y empaquetar

```powershell
.\mvnw.cmd verify
java -jar target/bookingnow-0.0.1-SNAPSHOT.jar
```
