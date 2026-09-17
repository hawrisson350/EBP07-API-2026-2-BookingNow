# HU-020101 - Registro de negocio

- Estado: backend implementado y probado; aceptación integral pendiente de frontend y entorno real
- Sprint: 1
- Fuente local: `workspace/Recuento HU.docx`
- Product Backlog Item: 10
- Fecha de incorporación: 2026-09-17
- SHA-256 del documento: `A3D7AEB6C478A303668DF2E32E5AC4DF6101813F5B10C295C1DD61B15944D029`

## Historia original

Como proveedor, quiero registrar un negocio indicando su información, para publicar y gestionar mis servicios en la plataforma.

## Índice de criterios de aceptación

Los identificadores CA se asignan para seguimiento; no agregan requisitos.

| ID | Criterio del documento |
|---|---|
| CA-01 | Disponibilidad de la opción de registro |
| CA-02 | Restricción de registro de negocio |
| CA-03 | Información del negocio |
| CA-04 | Modalidad virtual |
| CA-05 | Validación de campos obligatorios |
| CA-06 | Validación de datos |
| CA-07 | Registro exitoso |
| CA-08 | Confirmación |

## Criterios originales

Se conserva el texto extraído, incluidas concatenaciones y erratas del Word.
El índice anterior permite distinguir los escenarios sin cambiar su significado.

Disponibilidad de la opción de registro:

Dado que el usuario tiene un perfil de proveedor,

Cuando el usuario no tiene un negocio registrado en su cuenta,

Entonces el sistema debe mostrar la opción para registrar un negocio y permitir el acceso al formulario de registro.

Restricción de registro de negocio:

Dado que el proveedor ya tiene un negocio registrado en su cuenta,

Cuando acceda a la plataforma,

Entonces el sistema no debe mostrar la opción para registrar un nuevo negocio.

Información del negocio:

Dado que el proveedor se encuentra en el formulario de registro

Cuando diligencie la información del negocio

Entonces el sistema debe permitir registrar:

Nombre del negocio.

Correo electrónico.

Número de contacto.

Dirección.

Categoría.

Foto principal.

Galería de multimedia.

Modalidad virtual:

Dado que el proveedor está registrando su negocio

Cuando indique que el negocio ofrece sus servicios de manera virtual

Entonces el sistema debe permitir registrar el negocio sin exigir una dirección física.

Validación de campos obligatorios:

Dado que existen campos obligatorios sin diligenciar

Cuando el proveedor intente finalizar el registro

Entonces el sistema debe indicar los campos pendientes y no permitir completar el registro hasta que sean diligenciados Correctamente.

Validación de datos:

Dado que el proveedor ha ingresado la información del negocio

Cuando alguno de los datos no cumpla con el formato establecido

Entonces el sistema debe mostrar un mensaje indicando el error y solicitar la corrección de la información.

Registro exitoso:

Dado que el proveedor ha ingresado correctamente toda la información requerida

Cuando confirme el registro

Entonces el sistema debe crear el negocio y asociarlo a su cuenta.

Confirmación:

Dado que el negocio fue registrado correctamente

Cuando finalice el proceso

Entonces el sistema debe mostrar un mensaje confirmando que el negocio fue creado exitosamente.

## Observaciones de revisión inicial (no forman parte de la HU)

Negocio no está implementado. La HU impide registrar otro negocio cuando ya existe uno. Añade modalidad virtual (dirección no obligatoria) y galería multimedia, aunque inicialmente se había supuesto Multimedia fuera del sprint. Falta precisar campos obligatorios, formatos, catálogo de categorías y si fotos/galería se entregan como archivos o URLs, con sus límites. El ocultamiento de opciones corresponde al frontend; el backend también debe impedir duplicados y asociar el negocio al proveedor autenticado.

## Seguimiento

- Requisitos originales conservados; observaciones iniciales arriba son históricas.
- [Análisis](../analysis/HU-020101-analysis.md), [implementación](../implementation/HU-020101-implementation.md) y [pruebas](../testing/HU-020101-testing.md).
- Backend verificado el 2026-09-17; consultar matriz de criterios para pendientes.
- Decisión del usuario: un solo negocio por proveedor durante este sprint.
