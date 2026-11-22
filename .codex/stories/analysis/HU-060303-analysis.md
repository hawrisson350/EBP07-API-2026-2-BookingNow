# Análisis — HU-060303: Recibir notificación de cancelación

- Fecha: 2026-11-21
- Ficha origen: [../detail-user-stories/HU-060303-Recibir-notificacion-de-cancelacion.md](../detail-user-stories/HU-060303-Recibir-notificacion-de-cancelacion.md)
- Azure: [37](https://dev.azure.com/CodeFactory-2026-2/EBP07%20-%20Plataforma%20de%20Reservas%20de%20Servicios/_workitems/edit/37)
- Estado: implementación inicial de evento email; envío real pendiente de configuración/pruebas

## Requisitos confirmados

La historia y sus criterios se transcribieron desde el inventario recuperado y se vincularon con el work item de Azure. Ver ficha origen; no se agregan criterios.

## Comportamiento actual y diferencias

La cancelación publica email al cliente después del commit. Cliente recibe confirmación de cancelación; proveedor/administrador deben informar motivo y el mensaje invita a reprogramar. Resend aún no está configurado, así que no se entrega realmente.

## Dudas y supuestos por confirmar

Canal acordado: email. Falta configurar Resend y comprobar recepción real.

## Propuesta por capa hexagonal

La aplicación publica por `PublicarEmailPort`; el listener envía tras commit mediante `EnviarEmailPort`. El motivo es obligatorio en cancelaciones iniciadas por proveedor/administrador.

## Contrato HTTP y persistencia

El contrato definitivo depende de las preguntas anteriores. Conservar la separación: controlador REST → puerto de entrada/caso de uso → dominio → puerto de salida → adaptador JPA. No acoplar dominio a Spring MVC ni JPA. Para requisitos frontend puros no se propone endpoint.

## Matriz de criterios

| Criterio | Cambio propuesto | Comprobación |
|---|---|---|
| C1 | Analizar según regla acordada | Prueba automatizada/API o revisión accesible correspondiente; aún no ejecutada |
| C2 | Analizar según regla acordada | Prueba automatizada/API o revisión accesible correspondiente; aún no ejecutada |

## Dependencias y alcance pendiente

Backend involucrado. El estado de esta matriz es análisis; no representa pruebas realizadas ni funcionalidad disponible.
