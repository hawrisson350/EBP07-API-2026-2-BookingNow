# HU-040101 — Registrar recurso

**Fuente:** Azure DevOps, PBI 41, Sprint 2.

## Historia

Como proveedor, quiero registrar los recursos disponibles en mi negocio indicando su nombre, cantidad, estado y descripción, para tener un control de los recursos que pueden ser utilizados en la prestación de los servicios.

## Criterios de aceptación

- El recurso queda asociado al negocio seleccionado por el proveedor.
- El sistema solicita nombre, cantidad, estado y descripción.
- La cantidad es mayor que cero; no se aceptan valores negativos.
- Permite indicar el estado, por ejemplo: disponible, no disponible o en mantenimiento.
- Valida los campos obligatorios antes de guardar.
- Ante datos inválidos o incompletos, muestra el error y no guarda.
- Tras registrarse, aparece en el listado de recursos del negocio.
- Queda disponible para relacionarse posteriormente con uno o varios servicios y, si aplica, empleados.
