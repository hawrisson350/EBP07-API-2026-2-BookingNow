# HU-020202 — Configurar horario de atención del negocio

**Fuente:** Azure DevOps, PBI 106, Sprint 2.

## Historia

Como proveedor, quiero configurar el horario de atención de mi negocio durante su registro, indicando los días y horas en los que estará disponible, para que el sistema pueda conocer su disponibilidad y utilizarla posteriormente en la gestión de servicios y reservas.

## Criterios de aceptación

- El formulario de registro del negocio incorpora una sección «Horario de atención».
- Permite seleccionar los días de atención.
- Para cada día seleccionado, permite informar hora de apertura y de cierre.
- Los días sin atención pueden quedar sin seleccionar.
- No permite un cierre igual o anterior a la apertura.
- Si se selecciona un día, sus horas de apertura y cierre son obligatorias.
- El horario válido se guarda asociado al negocio registrado.
- Ante errores, informa el problema y no permite finalizar el registro hasta corregirlo.
- El horario puede consultarse en la información del negocio.
- El proveedor puede modificarlo posteriormente.

## Seguridad indicada en la HU

La validación de cierre posterior a apertura debe existir en backend, sin confiar únicamente en la interfaz.
