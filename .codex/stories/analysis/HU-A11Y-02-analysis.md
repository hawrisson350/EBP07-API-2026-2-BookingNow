# Análisis — HU-A11Y-02: Formularios accesibles y contraste AA

- Fecha: 2026-11-21
- Ficha origen: [../detail-user-stories/HU-A11Y-02-Formularios-accesibles-y-contraste-AA.md](../detail-user-stories/HU-A11Y-02-Formularios-accesibles-y-contraste-AA.md)
- Azure: [141](https://dev.azure.com/CodeFactory-2026-2/EBP07%20-%20Plataforma%20de%20Reservas%20de%20Servicios/_workitems/edit/141)
- Estado: análisis inicial; sin implementación de Sprint 3

## Requisitos confirmados

La historia y sus criterios se transcribieron desde el inventario recuperado y se vincularon con el work item de Azure. Ver ficha origen; no se agregan criterios.

## Comportamiento actual y diferencias

Requisito de interacción del frontend. La API no controla navegación por teclado, semántica HTML, contraste ni etiquetas visuales.

## Dudas y supuestos por confirmar

Acordar con frontend las rutas/componentes incluidos y cómo reportarán evidencia WCAG AA; la verificación será principalmente de interfaz.

## Propuesta por capa hexagonal

Sin cambio de backend identificado. Solo coordinar mensajes de validación y respuestas HTTP legibles para que el frontend los pueda presentar accesiblemente.

## Contrato HTTP y persistencia

El contrato definitivo depende de las preguntas anteriores. Conservar la separación: controlador REST → puerto de entrada/caso de uso → dominio → puerto de salida → adaptador JPA. No acoplar dominio a Spring MVC ni JPA. Para requisitos frontend puros no se propone endpoint.

## Matriz de criterios

| Criterio | Cambio propuesto | Comprobación |
|---|---|---|
| C1 | Sin cambio backend identificado | Prueba automatizada/API o revisión accesible correspondiente; aún no ejecutada |
| C2 | Sin cambio backend identificado | Prueba automatizada/API o revisión accesible correspondiente; aún no ejecutada |
| C3 | Sin cambio backend identificado | Prueba automatizada/API o revisión accesible correspondiente; aún no ejecutada |
| C4 | Sin cambio backend identificado | Prueba automatizada/API o revisión accesible correspondiente; aún no ejecutada |
| C5 | Sin cambio backend identificado | Prueba automatizada/API o revisión accesible correspondiente; aún no ejecutada |
| C6 | Sin cambio backend identificado | Prueba automatizada/API o revisión accesible correspondiente; aún no ejecutada |
| C7 | Sin cambio backend identificado | Prueba automatizada/API o revisión accesible correspondiente; aún no ejecutada |
| C8 | Sin cambio backend identificado | Prueba automatizada/API o revisión accesible correspondiente; aún no ejecutada |
| C9 | Sin cambio backend identificado | Prueba automatizada/API o revisión accesible correspondiente; aún no ejecutada |
| C10 | Sin cambio backend identificado | Prueba automatizada/API o revisión accesible correspondiente; aún no ejecutada |
| C11 | Sin cambio backend identificado | Prueba automatizada/API o revisión accesible correspondiente; aún no ejecutada |
| C12 | Sin cambio backend identificado | Prueba automatizada/API o revisión accesible correspondiente; aún no ejecutada |
| C13 | Sin cambio backend identificado | Prueba automatizada/API o revisión accesible correspondiente; aún no ejecutada |
| C14 | Sin cambio backend identificado | Prueba automatizada/API o revisión accesible correspondiente; aún no ejecutada |
| C15 | Sin cambio backend identificado | Prueba automatizada/API o revisión accesible correspondiente; aún no ejecutada |
| C16 | Sin cambio backend identificado | Prueba automatizada/API o revisión accesible correspondiente; aún no ejecutada |

## Dependencias y alcance pendiente

La HU no requiere cambio backend evidente. El estado de esta matriz es análisis; no representa pruebas realizadas ni funcionalidad disponible.
