# HU-010201 - Inicio de sesión con correo y contraseña

- Estado: backend implementado y probado; aceptación integral pendiente de frontend y entorno real
- Sprint: 1
- Fuente local: `workspace/Recuento HU.docx`
- Product Backlog Item: 9
- Fecha de incorporación: 2026-09-17
- SHA-256 del documento: `A3D7AEB6C478A303668DF2E32E5AC4DF6101813F5B10C295C1DD61B15944D029`

## Historia original

Como usuario, quiero iniciar sesión con mi correo y contraseña mediante una cuenta previamente registrada, para acceder a mi información personal en el sistema

## Índice de criterios de aceptación

Los identificadores CA se asignan para seguimiento; no agregan requisitos.

| ID | Criterio del documento |
|---|---|
| CA-01 | Inicio de sesión con credenciales correctas |
| CA-02 | Contraseña incorrecta |
| CA-03 | Correo no registrado |
| CA-04 | Campos obligatorios |
| CA-05 | Correo con formato incorrecto |
| CA-06 | Identificación del rol del usuario |
| CA-07 | Acceso según el rol |
| CA-08 | Cuenta inactiva o bloqueada |
| CA-09 | Protección de la contraseña |
| CA-10 | Tiempo de respuesta |

## Criterios originales

Se conserva el texto extraído, incluidas concatenaciones y erratas del Word.
El índice anterior permite distinguir los escenarios sin cambiar su significado.

Inicio de sesión con credenciales correctas:

Dado que el usuario tiene una cuenta previamente registrada y activa.

Cuando ingresa correctamente su correo y contraseña y selecciona "Iniciar sesión".

Entonces el sistema debe permitirle el acceso a la plataforma y mostrar las funcionalidades correspondientes a su cuenta.

Contraseña incorrecta:

Dado que el usuario ingresa un correo electrónico registrado y una contraseña incorrecta.

Cuando selecciona "Iniciar sesión".

Entonces el sistema debe impedir el acceso y mostrar el mensaje "Correo o contraseña incorrectos".

Correo no registrado:

Dado que el usuario ingresa un correo electrónico que no está asociado a una cuenta registrada.

Cuando selecciona "Iniciar sesión".

Entonces el sistema debe impedir el acceso y mostrar el mensaje "Correo o contraseña incorrectos", sin indicar si el correo está registrado o no.

Campos obligatorios:

Dado que el usuario se encuentra en la pantalla de inicio de sesión.

Cuando intenta iniciar sesión dejando vacío el correo, la contraseña o ambos campos.

Entonces el sistema debe indicar los campos que están incompletos y no permitir el inicio de sesión hasta que sean diligenciados.

Correo con formato incorrecto:

Dado que el usuario ingresa un correo electrónico con un formato inválido.

Cuando selecciona "Iniciar sesión".

Entonces el sistema debe indicarle que debe ingresar un correo electrónico válido y no debe permitir el acceso.

Identificación del rol del usuario:

Dado que el usuario ingresa credenciales válidas correspondientes a una cuenta registrada.

Cuando el sistema valida la información.

Entonces debe identificar el rol asociado a la cuenta, como cliente o proveedor y permitir el acceso de acuerdo con dicho rol.

Acceso según el rol:

Dado que el usuario ha iniciado sesión correctamente y su rol ha sido identificado.

Cuando ingresa a la plataforma.

Entonces debe visualizar las opciones y funcionalidades correspondientes a su rol y no debe tener acceso a funcionalidades que correspondan exclusivamente a otros roles.

Cuenta inactiva o bloqueada:

Dado que el usuario ingresa correctamente su correo y contraseña, pero su cuenta se encuentra inactiva o bloqueada.

Cuando selecciona "Iniciar sesión".

Entonces el sistema debe impedir el acceso e informar que la cuenta no se encuentra habilitada para iniciar sesión.

Protección de la contraseña:

Dado que el usuario se encuentra ingresando su contraseña.

Cuando escribe los caracteres en el campo correspondiente.

Entonces el sistema debe ocultar los caracteres de la contraseña para evitar que otras personas puedan visualizarla.

Tiempo de respuesta:

Dado que el usuario ha ingresado correctamente su correo y contraseña.

Cuando selecciona "Iniciar sesión".

Entonces el sistema debe mostrar el resultado del inicio de sesión en un tiempo máximo de 3 segundos, siempre que exista conexión disponible con la plataforma.

## Observaciones de revisión inicial (no forman parte de la HU)

El login actual usa nombreUsuario; debe pasar a correo. El error genérico debe decir exactamente "Correo o contraseña incorrectos". Faltan estado activo/bloqueado, validación específica de campos y una decisión sobre identificación automática del rol en un login común. El ocultamiento de contraseña es del frontend. El máximo de 3 segundos requiere definir medición y tratamiento del arranque en frío de Render Free; no está verificado.

## Seguimiento

- Requisitos originales conservados; observaciones iniciales arriba son históricas.
- [Análisis](../analysis/HU-010201-analysis.md), [implementación](../implementation/HU-010201-implementation.md) y [pruebas](../testing/HU-010201-testing.md).
- Backend verificado el 2026-09-17; consultar matriz de criterios para pendientes.
- Decisión del usuario: un solo negocio por proveedor durante este sprint.
