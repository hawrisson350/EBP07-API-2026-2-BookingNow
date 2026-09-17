# Sprint 1 — evidencia de verificación

- Fecha: 2026-09-17, finalización 06:57:34 -05:00.
- Código: cambios locales de este sprint, sin commit nuevo.
- Entorno: Windows, Java 21, Spring Boot 4.1.1, Maven Wrapper y H2.
- Comando: `.\mvnw.cmd -B -ntp verify`.
- Resultado: **BUILD SUCCESS; 18 pruebas, 0 fallos, 0 errores, 0 omitidas**.
- Duración total Maven: 26.327 segundos; JAR ejecutable generado.

Se verificó una copia aislada de `pom.xml`, `mvnw.cmd`, `.mvn` y `src` para evitar
interferencia del compilador del IDE sobre `target/classes`:

```text
C:\Users\hawri\AppData\Local\Temp\bookingnow-sprint-final-97a9b298-20bb-40d9-8a57-108ece050ffa
```

Log local: `target/verify-sprint.log` (ignorado por Git). Reportes Surefire en
`target/surefire-reports` de la copia aislada; no se publican salidas con datos de prueba.
La evidencia reproducible está en `BookingNowApplicationTests` (17 ejecuciones,
incluidas parametrizadas) y `CloudConfigurationTests` (1 ejecución).

## Comprobaciones adicionales

- JWT alterado, vencido, emisor/audiencia inválidos o claims requeridos ausentes: 401.
- Cuenta eliminada, inactiva o bloqueada: no reutiliza token.
- Credenciales incorrectas y correo desconocido comparten el mensaje exigido.
- Dos registros simultáneos de igual correo en roles distintos: un 201, un 409,
  una cuenta y una entrada en el registro de correo.
- Dos negocios simultáneos para el mismo proveedor: un 201, un 409, un negocio.
- Duración decimal rechazada sin truncarse a entero.
- Swagger y health accesibles; especificación incluye nuevos endpoints y Bearer.
- Perfil cloud valida la fixture de esquema en H2, sin crear tablas automáticamente.

## Fallo detectado y corregido

La primera ejecución produjo 17 aprobaciones y un fallo: una colisión de correo
en concurrencia devolvía 500. Los adaptadores de cuentas usaban `@Component`, por
lo que la excepción de `EntityManager.flush` no se traducía a la excepción de
integridad manejada por la API. Se cambió a `@Repository`; la ejecución completa
posterior devuelve 409 y pasa las 18 pruebas.

## Tiempo de login y límites

Medición HTTP local con aplicación iniciada: cliente 66 ms, proveedor 65 ms.
Son dos muestras de la ejecución, no un benchmark ni garantía de máximo 3 s en
producción. No se midió Render, arranque en frío, red externa ni carga concurrente
de usuarios para ese requisito.

H2 no acredita PostgreSQL real. No se ejecutaron migraciones, despliegue ni
pantallas del frontend. Los criterios visuales (opciones, mensajes, contraseña
oculta) siguen pendientes. Consultar las matrices individuales para cada CA.
