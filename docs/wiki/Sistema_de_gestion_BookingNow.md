<div align="center">

# BookingNow

### Sistema de gestión de reservas de servicios

**Análisis y Diseño de Sistemas II** Universidad de Antioquia - Facultad de Ingeniería - Ingeniería de Sistemas

**Entrega formal — Sprint 1** Versión 0.1 - Septiembre de 2026

</div>

---

## Equipo de trabajo

| Integrante | Rol en la entrega |
|---|---|
| Manuela Restrepo Aguirre | Integrante |
| Laura María Valencia Manco | Integrante |
| Xiomara Echavarría Gallego | Integrante |
| Hawrisson Delvis Avendaño Misse | Integrante |
| Juan Manuel Mejía Nervaez | Integrante |

**Tutor:** Grissa Vianney Maturana González

## Contenido

- [Descripción general](#descripción-general)
- [Objetivo general](#objetivo-general)
- [Alcance](#alcance)
- [Repositorio](#repositorio)
- [Estado del proyecto](#estado-del-proyecto)
- [Entrega del Sprint 1](Sprint_1.md)

---

# Descripción general

El presente proyecto propone el desarrollo de un sistema digital orientado a facilitar la gestión de reservas de servicios, con el propósito de optimizar los procesos administrativos asociados a la programación de citas y al control de disponibilidad de recursos.

Negocios como clínicas, consultorios, salones de belleza y centros deportivos dependen en gran medida de sistemas de reservas eficientes para administrar su agenda y la disponibilidad de sus recursos. Sin embargo, la ausencia de herramientas adecuadas suele derivar en sobreocupación de horarios, cancelaciones desorganizadas y dificultades generales para la gestión de las agendas, afectando tanto la operación interna del negocio como la experiencia del cliente.

Frente a esta problemática dirigimos el sistema a dos tipos de usuarios principales: clientes **y** proveedores de servicios**.** Los clientes podrán registrarse en la plataforma, acceder a los distintos negocios disponibles, realizar reservas y consultar información relevante sobre los servicios ofrecidos. Por su parte, los proveedores podrán registrarse y establecer su propio negocio dentro del sistema, gestionar su agenda, controlar la disponibilidad de sus recursos, realizar seguimiento a las reservas registradas, recibir notificaciones sobre confirmaciones o cancelaciones, así como registrar a sus empleados y asignarlos a los servicios correspondientes.

De esta manera, el sistema busca optimizar el proceso de agendamiento, eliminar conflictos en la asignación de citas y mejorar la organización del servicio, brindando así una experiencia ágil, confiable y autónoma tanto para los clientes como para los proveedores. Este desarrollo se enmarca dentro del valor agregado que representa para el negocio la optimización en el uso de recursos, la mejora en la experiencia del cliente y una mayor eficiencia en la gestión de las agendas.

# Objetivo general

Nuestro objetivo es desarrollar una plataforma digital que permita la administración integral en los procesos de reserva de servicios, optimización en la gestión de agendas, disponibilidad de recursos y la administración de negocios.

| N.º | Objetivo |
|---:|---|
| 1 | Implementar un módulo de gestión de usuarios que permita el registro, autenticación y administración de perfiles de clientes y proveedores. |
| 2 | Permitir a los proveedores registrar, consultar, editar y activar o desactivar sus negocios dentro de la plataforma. |
| 3 | Desarrollar un módulo de gestión de servicios que facilite el registro, consulta, edición y control del estado de los servicios ofrecidos por cada negocio. |
| 4 | Diseñar un módulo de control de recursos que permita registrar, consultar y gestionar la disponibilidad de los recursos asociados a los servicios. |
| 5 | Implementar un módulo de reservas que permita registrar, modificar, cancelar y consultar el historial de reservas, incluyendo notificaciones automáticas de confirmación, recordatorio y cancelación. |
| 6 | Habilitar la gestión de empleados, permitiendo su registro, asignación a servicios, definición de horarios y verificación de disponibilidad para su asignación a reservas. |
| 7 | Generar reportes de ocupación, uso de servicios y cancelaciones que sirvan como apoyo a la toma de decisiones administrativas de los proveedores. |

# Alcance

El alcance esperado del proyecto incluye:

- Gestión de Usuarios: El proyecto contempla el registro, inicio de
sesión, gestión y actualización de perfiles además de su recuperación para clientes y proveedores.
- Gestión de Negocios: El proyecto contempla el registro, consulta,
edición, activación e inactivación de negocios al igual que su búsqueda y consulta de historial.
- Gestión de Servicios: El proyecto contempla, consultar, editar y
activación e inactivación de servicios además de la consulta del historial de servicios.
- Gestión de Recursos: El proyecto contempla el apartado para
registrar recursos y asociarlos a servicios, consultar cantidades, detalles y búsqueda y gestión de su disponibilidad
- Gestión de Reservas: El proyecto contempla el registro, modificación
,cancelación y consulta de historial de reservas.
- Gestión de Empleados: El proyecto contempla el registro, consultar y
buscar empleados, también su activación e inactivación, modificar horarios y asignaciones con verificación de disponibilidad.
- Gestión de Reportes: El proyecto contempla la consulta y filtrado de
reservas, además de análisis de ocupaciones y uso de servicios, cancelaciones y extracción de reportes

El proyecto no incluye:

- Procesamiento de pagos en línea o integración con pasarelas de pago.
- Facturación electrónica o generación de comprobantes fiscales.
- Módulo de mensajería o chat en tiempo real entre cliente y
proveedor.
- Administración de nóminas o pagos a empleados.
- Módulo de calificaciones o reseñas de servicios/negocios.

# Repositorio

| Componente | Código fuente | Aplicación desplegada |
|---|---|---|
| Frontend React | [Repositorio del frontend](https://github.com/luisayarurob/BookingNow-) | [BookingNow en Vercel](https://booking-now-theta.vercel.app/) |
| Backend Spring Boot | [Repositorio de la API](https://github.com/hawrisson350/EBP07-API-2026-2-BookingNow) | [API en AWS](http://3.136.161.165:8080) · [Swagger](http://3.136.161.165:8080/swagger-ui.html) |

# Estado del proyecto

| Sprint | Estado |
|---|---|
| Sprint 1 | ✅ Completado |
| Sprint 2 | 🟡 En proceso |
| Sprint 3 | 🟡 En proceso |


---

## Entrega del Sprint 1

La documentación detallada, los mockups, diagramas, evidencias y anexos del primer sprint se encuentran en [Sprint_1.md](Sprint_1.md).
