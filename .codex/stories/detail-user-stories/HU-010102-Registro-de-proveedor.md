# HU-010102 - Registro de proveedor con razón social y NIT

- Estado: backend implementado y probado; aceptación integral pendiente de frontend y entorno real
- Sprint: 1
- Fuente local: `workspace/Recuento HU.docx`
- Product Backlog Item: 7
- Fecha de incorporación: 2026-09-17
- SHA-256 del documento: `A3D7AEB6C478A303668DF2E32E5AC4DF6101813F5B10C295C1DD61B15944D029`

## Historia original

Como usuario, quiero registrarme con razón social y NIT de mi empresa, para crear una cuenta en el sistema

## Índice de criterios de aceptación

Los identificadores CA se asignan para seguimiento; no agregan requisitos.

| ID | Criterio del documento |
|---|---|
| CA-01 | Registro exitoso |
| CA-02 | Correo inválido |
| CA-03 | Razón social inválida |
| CA-04 | NIT Inválido |
| CA-05 | Contraseña inválida |
| CA-06 | Nombre de usuario inválido |
| CA-07 | Correo ya registrado |
| CA-08 | Razón social ya registrada |
| CA-09 | NIT ya registrado |

## Criterios originales

Se conserva el texto extraído, incluidas concatenaciones y erratas del Word.
El índice anterior permite distinguir los escenarios sin cambiar su significado.

Escenario: Registro exitoso

Dado que no tengo una cuenta en la aplicación

Y que ingreso un correo electrónico con formato usuario@dominio.extensión

Y que ingreso una razón social con mínimo de 3 caracteres 

Y que ingreso un NIT con formato #########-#, compuesto por 9 dígitos, un guion y un dígito de verificación

Y que ingreso un nombre de usuario con mínimo de 3 caracteres

Y una contraseña segura con mínimo 8 caracteres, con al menos una letra, un número y un carácter especial

Cuando el correo no está registrado previamente

Y el NIT no está registrado previamente

Y la razón social no está registrada previamente

Entonces el sistema debe crear la cuenta exitosamente

Y mostrar un mensaje de confirmación

Escenario: Correo inválido

Dado que el usuario ingresa un correo con formato incorrecto

Cuando intenta registrarse

Entonces el sistema debe denegar la creación de la cuenta

Y mostrar un mensaje de error indicando que el correo no es válido.

Escenario: Razón social inválida

Dado que el usuario ingresa una razón social con formato incorrecto

Cuando intenta registrarse 

Entonces el sistema debe denegar la creación de la cuenta

Y mostrar un mensaje de error indicando que la razón social no es válida.

Escenario: NIT Inválido

Dado que el usuario ingresa un NIT con formato incorrecto

Cuando intenta registrarse 

Entonces el sistema debe denegar la creación de la cuenta

Y mostrar un mensaje de error indicando que el NIT no es válido.

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

Entonces el sistema debe denegar la creación de la cuentaEscenario: Razón social ya registrada

Dado que el usuario ingresa una razón social ya registrada en la plataforma

Cuando intenta registrarse

Entonces el sistema debe denegar la creación de la cuenta

Escenario: NIT ya registrado

Dado que el usuario ingresa un NIT ya registrado en la plataforma

Cuando intenta registrarse

Entonces el sistema debe denegar la creación de la cuenta

## Observaciones de revisión inicial (no forman parte de la HU)

Pendiente validar razón social de mínimo 3 caracteres, NIT con 9 dígitos-guion-1 dígito y unicidad de correo, razón social y NIT. La contraseña también exige letra, número y carácter especial. La HU describe el formato del dígito de verificación; no exige explícitamente calcularlo. Confirmar normalización de razón social y alcance de unicidad del correo.

## Seguimiento

- Requisitos originales conservados; observaciones iniciales arriba son históricas.
- [Análisis](../analysis/HU-010102-analysis.md), [implementación](../implementation/HU-010102-implementation.md) y [pruebas](../testing/HU-010102-testing.md).
- Backend verificado el 2026-09-17; consultar matriz de criterios para pendientes.
- Decisión del usuario: un solo negocio por proveedor durante este sprint.
