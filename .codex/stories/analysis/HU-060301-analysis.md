# Análisis — HU-060301: Recibir confirmación de reserva

- Fecha: 2026-11-21
- Ficha origen: [../detail-user-stories/HU-060301-Recibir-confirmacion-de-reserva.md](../detail-user-stories/HU-060301-Recibir-confirmacion-de-reserva.md)
- Azure: [35](https://dev.azure.com/CodeFactory-2026-2/EBP07%20-%20Plataforma%20de%20Reservas%20de%20Servicios/_workitems/edit/35)
- Estado: adaptador inicial implementado; envío real pendiente de configuración/pruebas

## Requisitos confirmados

La historia y sus criterios se transcribieron desde el inventario recuperado y se vincularon con el work item de Azure. Ver ficha origen; no se agregan criterios.

## Comportamiento actual y diferencias

El registro de reserva publica una confirmación email después del commit. Se creó `EnviarEmailPort`, evento tras commit y adaptador Resend. Sin clave y remitente, el adaptador registra `no_configurado` y no envía. La reserva queda persistida como CONFIRMADA.

## Dudas y supuestos por confirmar

El equipo eligió email y Resend. Falta configurar la clave y el remitente permitido; probar recepción real. El recordatorio de HU-060302 quedó definido a 24 horas.

## Propuesta por capa hexagonal

Implementado el puerto de email y su adaptador Resend desacoplado. Envío posterior al commit evita enviar confirmación si falla la transacción. El scheduler del recordatorio se documenta en el análisis de HU-060302.

## Contrato HTTP y persistencia

El contrato definitivo depende de las preguntas anteriores. Conservar la separación: controlador REST → puerto de entrada/caso de uso → dominio → puerto de salida → adaptador JPA. No acoplar dominio a Spring MVC ni JPA. Para requisitos frontend puros no se propone endpoint.

## Matriz de criterios

| Criterio | Cambio propuesto | Comprobación |
|---|---|---|
| C1 | Analizar según regla acordada | Prueba automatizada/API o revisión accesible correspondiente; aún no ejecutada |
| C2 | Analizar según regla acordada | Prueba automatizada/API o revisión accesible correspondiente; aún no ejecutada |

## Dependencias y alcance pendiente

Backend involucrado. El estado de esta matriz es análisis; no representa pruebas realizadas ni funcionalidad disponible.
