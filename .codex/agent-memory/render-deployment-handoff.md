# Relevo: despliegue BookingNow en Render

Actualizado: 2026-09-17 (America/Bogota)

## Objetivo

Desplegar `bookingnow-api`, una API Java 21 / Spring Boot 4.1.1, en Render Free. La base de datos es PostgreSQL administrada en Supabase. El servicio fue creado como Docker Web Service y el health check configurado es `/health`.

## Estado actual

- Repositorio: rama `main`.
- Último commit: `de29ddb` (`Revert "test(deploy): aisla JPA para verificar health en Render"`).
- Servicio Render: `bookingnow-api`.
- URL pública esperada: `https://bookingnow-api.onrender.com`.
- El despliegue de diagnóstico `dep-dam0cpajnfac73crcf5g` sigue/seguía en progreso al crear este documento; no se debe tomar como una versión funcional.
- La configuración temporal que excluía JPA fue revertida para no dejar la API sin persistencia.
- No hay secretos en este archivo. No copiar ni registrar contraseñas, `JWT_SECRET` ni URLs que contengan password.

## Configuración efectiva del proyecto

`render.yaml` define Docker, plan Free, `/health`, perfil `cloud`, `JAVA_TOOL_OPTIONS`, URL de pooler transaccional Supabase por el puerto 6543, usuario y variables secretas para contraseña/JWT/CORS.

`application.properties` YA contiene:

```properties
server.port=${PORT:8080}
management.endpoints.web.base-path=/
management.endpoints.web.exposure.include=health
```

Esto significa que, cuando Spring complete el arranque en Render, debe escuchar el valor de `PORT` (Render suele dar `10000`). No agregar un segundo `server.port` en `application-cloud.properties`.

El Dockerfile actual genera el JAR con:

```dockerfile
RUN ./mvnw -B -ntp package -Dmaven.test.skip=true
```

Luego lo inicia con:

```dockerfile
ENTRYPOINT ["java", "-jar", "app.jar"]
```

La omisión de pruebas solo afecta el build del contenedor. No afecta el runtime.

## Evidencia obtenida en Render

1. Los builds Docker terminan con `BUILD SUCCESS`.
2. Render usa el Dockerfile correcto; Docker Command está vacío, por lo que no sobreescribe el `ENTRYPOINT`.
3. Render detecta el intento de Java mediante:

```text
Picked up JAVA_TOOL_OPTIONS: -Xms64m -Xmx256m -XX:+UseSerialGC -XX:+ExitOnOutOfMemoryError
```

4. En el despliegue `dep-dam085vcgkoc73frf5s0` (commit `2b12831`) sí aparecieron:

```text
Starting BookingNowApplication ...
The following 1 profile is active: "cloud"
Starting service [Tomcat]
Starting Servlet engine: [Apache Tomcat/11.0.24]
Class org.postgresql.Driver found ...
```

Después no apareció `Tomcat started on port ...`; Render reportó `No open ports detected` y esperó `/health` en el puerto 10000.

5. En el despliegue aislado sin DataSource/JPA (`dep-dam0cpajnfac73crcf5g`, commit `4fb8c87`) solo apareció `Picked up JAVA_TOOL_OPTIONS`; tras aproximadamente tres minutos no apareció ni el banner de Spring. Por eso esta prueba NO demuestra que Supabase sea la única causa. Puede existir una demora/no salida de logs del proceso Java o un problema previo al contexto de Spring.

## Intentos y conclusiones

| Commit | Cambio | Resultado |
|---|---|---|
| `fd4908b` | Conexión directa Supabase | Falló: la ruta directa usaba IPv6 y Render informó red no alcanzable. |
| `2d4a93f` | Pooler IPv4 | Cambió a pooler. |
| `f96b0b2` | TLS desactivado temporalmente | No solucionó; se revirtió. |
| `a12d264`, `35139ab` | TLS y logs JDBC/Hikari | Se obtuvieron errores TLS del pooler. |
| `fd60704` | Pooler transaccional 6543 + `sslmode=require&prepareThreshold=0` | Continuó fallando/esperando. |
| `f830ed7` | Reintentos Hikari, sin validar esquema | No publicó health. |
| `449813b` | `spring.main.lazy-initialization=true` | Quedó esperando health por más de 15 min. |
| `d24eda0` | Omitir pruebas en build Docker | Build correcto; no cambia runtime. |
| `ab7c723` | Puerto explícito en cloud | Revertido: el puerto ya estaba configurado globalmente. |
| `4fb8c87` | Excluir temporalmente DataSource/JPA | Revertido: tampoco llegó a banner Spring durante el tiempo observado. |

## Conexión Supabase: evidencia anterior

- Directa: fallo de red no alcanzable, consistente con que la conexión directa de proyectos Free depende de IPv6.
- Pooler session (5432): se observó `SSLHandshakeException` / `SSL peer shut down incorrectly`.
- Pooler transaction (6543): también hubo error TLS en intentos anteriores.
- La URL JDBC actualmente configurada es la del pooler transaccional con `sslmode=require&prepareThreshold=0`; es la configuración que recomienda Supabase para JDBC con transaction mode.
- No asumir que un segundo intento "despierta" Supabase. Los proyectos Free pausados requieren reanudación desde Supabase Dashboard.

## Cambios vigentes en application-cloud.properties

- Perfil `cloud`.
- Logs DEBUG de Hikari/PostgreSQL y TRACE de algunos componentes Hibernate.
- `spring.main.lazy-initialization=true`.
- Hikari: pool 3, mínimo idle 0, timeout de conexión 10 s, validation 5 s, `initialization-fail-timeout=-1`.
- Hibernate: `ddl-auto=none`, dialecto PostgreSQL explícito, sin metadatos JDBC al boot.

Estos cambios se introdujeron para tolerar fallos de Supabase, pero también hacen el arranque más difícil de interpretar. Considerar volver a una configuración más simple una vez se aísle el problema.

## Próximos pasos recomendados

Cambiar una variable por vez y conservar evidencia de cada resultado.

1. **Revisar el proceso Java antes de Spring.**
   - Usar Shell de Render si está habilitado o reemplazar TEMPORALMENTE el Docker `ENTRYPOINT` por un wrapper que imprima `java -version`, `echo PORT=$PORT` (sin secretos) y después use `exec java -jar app.jar`.
   - Objetivo: saber si el bloqueo ocurre antes de cargar Spring o si Render solo atrasa los logs.
   - No cambiar URL de base de datos en esta prueba.

2. **Probar el JAR local con perfil cloud y valores de prueba.**
   - Confirmar que, sin depender de Render, el proceso imprime banner y abre el puerto.
   - No imprimir secretos en consola ni en commits.

3. **Cuando Spring llegue a abrir Tomcat, verificar `/health`.**
   - `GET https://bookingnow-api.onrender.com/health` debe devolver HTTP 200.
   - Si el puerto se abre pero el health falla, revisar `management.health.db.enabled` y el indicador de base de datos.

4. **Solo después aislar JDBC.**
   - Conservar 6543, TLS y `prepareThreshold=0`.
   - Verificar las variables efectivas en Render Environment: host, puerto, base y usuario; la contraseña debe quedar oculta.
   - Comparar con la cadena oficial que ofrece Supabase para transaction pooler.

5. **Restaurar configuración productiva.**
   - Quitar cualquier wrapper temporal.
   - Restaurar JPA y probar una consulta real autenticada.

## Comandos locales útiles

```powershell
.\mvnw.cmd -B -ntp package '-Dmaven.test.skip=true'
git log --oneline -15
git status --short
```

## Advertencias de alcance

- No borrar datos ni regenerar secretos.
- `workspace/` es local y no debe subirse a Git.
- Mantener arquitectura hexagonal; esta investigación solo afecta infraestructura y configuración de despliegue.

## Actualizacion posterior: resultado de la prueba aislada

El despliegue aislado `dep-dam0cpajnfac73crcf5g` finalmente si alcanzo a atender solicitudes HTTP. Render comenzo a consultar `/health` y lo repitio al recibir error. Por la inicializacion diferida, la primera solicitud intenta crear `SecurityFilterChain` y el `JwtDecoder`. `JwtConfig` necesita `ClienteRepositoryAdapter`, el cual necesita `ClienteJpaRepository`.

La prueba temporal excluia los repositorios JPA, por eso cada comprobacion fallaba con:

```text
No qualifying bean of type '...ClienteJpaRepository' available
```

Esto explica las excepciones repetidas: son reintentos del health check de Render ante un perfil temporal inconsistente, no reintentos de Hikari ni evidencia nueva de un fallo de Supabase. El commit de esa prueba (`4fb8c87`) ya fue revertido por `de29ddb`; la rama `main` actual vuelve a incluir JPA. El despliegue temporal se canceló desde Render el 2026-09-17.

No usar el perfil que excluye JPA como prueba de conectividad. La próxima prueba debe hacerse con la aplicación completa y, si es necesario aislar `/health`, configurar un health check que no fuerce la seguridad/JWT ni eliminar repositorios requeridos por esa configuración.
