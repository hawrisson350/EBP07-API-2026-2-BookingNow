# Análisis — HU-060302: Recibir recordatorio de reserva

- Fecha: 2026-11-21
- Ficha origen: [../detail-user-stories/HU-060302-Recibir-recordatorio-de-reserva.md](../detail-user-stories/HU-060302-Recibir-recordatorio-de-reserva.md)
- Azure: [36](https://dev.azure.com/CodeFactory-2026-2/EBP07%20-%20Plataforma%20de%20Reservas%20de%20Servicios/_workitems/edit/36)
- Estado: implementación programada a 24 horas; falta configurar correo y probar

## Requisitos confirmados

La historia y sus criterios se transcribieron desde el inventario recuperado y se vincularon con el work item de Azure. Ver ficha origen; no se agregan criterios.

## Comportamiento actual y diferencias

Hay un scheduler cada cinco minutos que busca reservas `ACTIVA`/`CONFIRMADA` dentro de las próximas 24 horas; excluye canceladas y marca cada reserva para evitar repetir notificaciones. Resend sin configurar desactiva el scheduler.

## Dudas y supuestos por confirmar

El equipo eligió email y 24 horas antes. Falta configurar Resend y probar recepción. Si se crea una reserva con menos de 24 horas de anticipación, se procesa en la siguiente ejecución; actualmente no hay reintento tras fallar el proveedor.

## Propuesta por capa hexagonal

Implementado el puerto de email, adaptador Resend y proceso programado. El envío se publica después del commit. El scheduler solo se activa cuando están configurados la clave y el remitente de Resend.

## Contrato HTTP y persistencia

El contrato definitivo depende de las preguntas anteriores. Conservar la separación: controlador REST → puerto de entrada/caso de uso → dominio → puerto de salida → adaptador JPA. No acoplar dominio a Spring MVC ni JPA. Para requisitos frontend puros no se propone endpoint.

## Matriz de criterios

| Criterio | Cambio propuesto | Comprobación |
|---|---|---|
| C1 | Analizar según regla acordada | Prueba automatizada/API o revisión accesible correspondiente; aún no ejecutada |
| C2 | Analizar según regla acordada | Prueba automatizada/API o revisión accesible correspondiente; aún no ejecutada |
| C3 | Analizar según regla acordada | Prueba automatizada/API o revisión accesible correspondiente; aún no ejecutada |

## Dependencias y alcance pendiente

Backend involucrado. El estado de esta matriz es análisis; no representa pruebas realizadas ni funcionalidad disponible.
