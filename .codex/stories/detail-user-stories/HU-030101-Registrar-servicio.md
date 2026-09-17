# HU-030101 - Registrar Servicio

- Estado: backend implementado y probado; aceptación integral pendiente de frontend y entorno real
- Sprint: 1
- Fuente local: `workspace/Recuento HU.docx`
- Product Backlog Item: 13
- Fecha de incorporación: 2026-09-17
- SHA-256 del documento: `A3D7AEB6C478A303668DF2E32E5AC4DF6101813F5B10C295C1DD61B15944D029`

## Historia original

Como proveedor, quiero registrar un servicio asociado a uno de mis negocios, indicando su nombre, imagen de referencia, duración, precio y descripción, para tenerlo disponible en el sistema.

## Índice de criterios de aceptación

Los identificadores CA se asignan para seguimiento; no agregan requisitos.

| ID | Criterio del documento |
|---|---|
| CA-01 | Negocio asociado |
| CA-02 | Datos del servicio |
| CA-03 | Imagen de referencia |
| CA-04 | Duración válida |
| CA-05 | Precio válido |
| CA-06 | Validación de información |
| CA-07 | Asociación con el proveedor |
| CA-08 | Disponibilidad del servicio |

## Criterios originales

Se conserva el texto extraído, incluidas concatenaciones y erratas del Word.
El índice anterior permite distinguir los escenarios sin cambiar su significado.

  Negocio asociado

El proveedor debe seleccionar uno de sus negocios para asociar el servicio.

  Datos del servicioEl sistema debe solicitar el nombre, duración, precio y descripción del servicio.

  Imagen de referenciaEl sistema debe permitir al proveedor agregar una imagen de referencia del servicio.

  Duración válidaLa duración del servicio debe ser un valor mayor a 0 minutos.

  Precio válidoEl precio del servicio debe ser un valor igual o mayor a 0 y no debe aceptar valores negativos.

  Validación de informaciónSi falta algún dato obligatorio o se ingresa información inválida, el sistema debe mostrar un mensaje indicando el error y no permitir guardar el servicio.

  Asociación con el proveedorEl servicio debe quedar asociado al negocio seleccionado y, por medio de este, al proveedor que lo registró.

  Disponibilidad del serviciofácilUna vez registrado correctamente, el servicio debe aparecer en el listado de servicios del negocio y quedar disponible para utilizarse en las reservas.

## Observaciones de revisión inicial (no forman parte de la HU)

Servicio no está implementado. Se exige duración > 0, precio >= 0, asociación a un negocio propio y aparición en su listado. "Uno de mis negocios" contrasta con la restricción de una sola empresa en HU-020101; confirmar cardinalidad. Definir obligatoriedad de imagen, almacenamiento, moneda/precisión y si la duración admite fracciones. La disponibilidad para reservas no describe un caso de uso de creación de reservas.

## Seguimiento

- Requisitos originales conservados; observaciones iniciales arriba son históricas.
- [Análisis](../analysis/HU-030101-analysis.md), [implementación](../implementation/HU-030101-implementation.md) y [pruebas](../testing/HU-030101-testing.md).
- Backend verificado el 2026-09-17; consultar matriz de criterios para pendientes.
- Decisión del usuario: un solo negocio por proveedor durante este sprint.
