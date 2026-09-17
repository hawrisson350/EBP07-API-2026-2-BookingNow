# HU-010101 - Registro de cliente con correo y contraseña

- Estado: backend implementado y probado; aceptación integral pendiente de frontend y entorno real
- Sprint: 1
- Fuente local: `workspace/Recuento HU.docx`
- Product Backlog Item: 6
- Fecha de incorporación: 2026-09-17
- SHA-256 del documento: `A3D7AEB6C478A303668DF2E32E5AC4DF6101813F5B10C295C1DD61B15944D029`

## Historia original

Como usuario, quiero registrarme con un correo y contraseña, para crear una cuenta en el sistema.

## Índice de criterios de aceptación

Los identificadores CA se asignan para seguimiento; no agregan requisitos.

| ID | Criterio del documento |
|---|---|
| CA-01 | Registro exitoso |
| CA-02 | Correo inválido |
| CA-03 | Contraseña inválida |
| CA-04 | Nombre de usuario inválido |
| CA-05 | Correo ya registrado |

## Criterios originales

Se conserva el texto extraído, incluidas concatenaciones y erratas del Word.
El índice anterior permite distinguir los escenarios sin cambiar su significado.

Escenario: Registro exitoso

Dado que no tengo una cuenta en la aplicación

Y que ingreso un correo electrónico con formato usuario@dominio.extensiónY que ingreso un nombre de usuario con mínimo 3 caracteres

Y una contraseña segura con mínimo 8 caracteres, con al menos una letra, un número y un carácter especial

Cuando el correo no está registrado previamente

Entonces el sistema debe crear la cuenta exitosamente

Y mostrar un mensaje de confirmación

Escenario: Correo inválido

Dado que el usuario ingresa un correo con formato incorrecto

Cuando intenta registrarse

Entonces el sistema debe denegar la creación de la cuenta

Y mostrar un mensaje de error indicando que el correo no es válido.

Escenario: Contraseña inválida 

Dado que el usuario ingrese una contraseña con formato incorrecto

Cuando intenta registrarse

Entonces el sistema debe denegar la creación de la cuenta

Y mostrar un mensaje de error indicando que la contraseña no es válida.

Escenario: Nombre de usuario inválido 

Dado que el usuario ingrese un nombre de usuario con formato incorrecto

Cuando intenta registrarse

Entonces el sistema debe denegar la creación de la cuenta

Y mostrar un mensaje de error indicando que el nombre de usuario no es válido.

Escenario: Correo ya registrado

Dado que el usuario ingresa un correo ya registrado en la plataforma

Cuando intenta registrarse

Entonces el sistema debe denegar la creación de la cuenta

Y mostrar un mensaje indicando que el correo ya está en uso.

## Observaciones de revisión inicial (no forman parte de la HU)

El registro actual valida formato de correo y longitud de contraseña, pero no unicidad de correo ni presencia obligatoria de letra, número y carácter especial. El nombre de usuario sí sigue siendo requerido por esta HU. Confirmar si el correo debe ser único entre clientes y proveedores y qué caracteres se admiten en nombreUsuario; el código aplica restricciones adicionales.

## Seguimiento

- Requisitos originales conservados; observaciones iniciales arriba son históricas.
- [Análisis](../analysis/HU-010101-analysis.md), [implementación](../implementation/HU-010101-implementation.md) y [pruebas](../testing/HU-010101-testing.md).
- Backend verificado el 2026-09-17; consultar matriz de criterios para pendientes.
- Decisión del usuario: un solo negocio por proveedor durante este sprint.
