# HU-050101 — Registrar empleado

**Fuente:** Azure DevOps, PBI 69, Sprint 2.

## Historia

Como proveedor, quiero registrar los empleados de mi negocio indicando sus datos personales y de contacto, para tenerlos disponibles en el sistema y poder relacionarlos posteriormente con los servicios y recursos correspondientes.

## Criterios de aceptación

- El empleado queda asociado al negocio seleccionado por el proveedor.
- Solicita nombres, número de documento de identidad (CC), teléfono y correo electrónico.
- Valida campos obligatorios antes de guardar.
- El documento tiene formato válido y no permite dos empleados con el mismo documento dentro del mismo negocio.
- El correo tiene formato válido.
- Si hay datos inválidos, informa el error y no guarda.
- Tras registrarse, aparece en el listado de empleados del negocio.
- Queda disponible para asignación posterior a uno o varios servicios y, si aplica, recursos.

## Seguridad indicada en la HU

La HU solicita impedir el registro repetido de empleados con la misma cédula y propone definir un límite de empleados por negocio para la versión básica.
