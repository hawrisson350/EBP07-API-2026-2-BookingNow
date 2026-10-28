# Sprint 2 — Reservas y gestión de servicios

> [← Volver al documento general del proyecto](Sistema_de_gestion_BookingNow.md) · [Ir a Sprint 1](Sprint_1.md)

## Objetivo

Para este segundo sprint, el sistema permitirá a los usuarios realizar reservas, visualizar y buscar servicios según sus necesidades. Los proveedores podrán registrar y asignar empleados y recursos.

## Corrección de errores del primer sprint

El documento fuente incluye este apartado, pero no describe errores concretos ni las correcciones realizadas. Por eso, esta entrega no agrega detalles que no estén documentados.

## Diagramas del diseño

### Diagrama de clases

<p align="center">
  <img src="https://raw.githubusercontent.com/hawrisson350/EBP07-API-2026-2-BookingNow/main/docs/wiki/media/sprint-2/diagrama-clases.png" alt="Diagrama de clases del Sprint 2" width="900">
</p>

### BPMN

<p align="center">
  <img src="https://raw.githubusercontent.com/hawrisson350/EBP07-API-2026-2-BookingNow/main/docs/wiki/media/sprint-2/bpmn-sprint-2.jpg" alt="Diagrama BPMN del Sprint 2" width="900">
</p>

## Historias de usuario y diagramas de actividades

Las siguientes historias y flujos se transcriben del documento de entrega del Sprint 2. El archivo fuente describe las actividades; no presenta una sección independiente de criterios de aceptación.

### HU-060101 — Registrar reserva con fecha y hora

**Como usuario**, quiero registrar una reserva seleccionando un servicio, una fecha y una hora disponibles, para asegurar mi cupo y asociar la reserva con mis datos como cliente.

**Flujo descrito:**

1. El usuario elige el servicio y pulsa **Reservar**. El sistema verifica que haya una sesión iniciada. Si no la hay, solicita iniciar sesión y termina el flujo.
2. El sistema muestra el formulario, identifica si el servicio requiere empleado, recurso o ambos y consulta cuáles están disponibles. El usuario elige los que correspondan.
3. El sistema consulta la disponibilidad y el horario del negocio. El usuario elige la fecha y luego una hora, según la duración del servicio.
4. La reserva se asocia al cliente autenticado y se muestra un resumen para confirmar o cancelar.
5. Si el usuario confirma, el sistema vuelve a verificar la disponibilidad del empleado, recurso, fecha y hora. Si algo cambió, informa y permite consultar otra opción.
6. Si siguen disponibles, guarda la reserva, bloquea el horario, envía un correo con las opciones **Confirmar cita** y **Cancelar cita**, y muestra la confirmación.

<p align="center">
  <img src="https://raw.githubusercontent.com/hawrisson350/EBP07-API-2026-2-BookingNow/main/docs/wiki/media/sprint-2/HU-060101-reservar-servicio.png" alt="Diagrama de actividades para registrar una reserva" width="900">
</p>

### HU-040101 — Registrar recurso

**Como proveedor**, quiero registrar los recursos disponibles en mi negocio indicando su nombre, cantidad, estado y descripción, para controlar los recursos que pueden utilizarse en la prestación de los servicios.

**Flujo descrito:**

1. El proveedor selecciona el negocio y elige **Registrar recurso**.
2. El sistema muestra un formulario con nombre, cantidad, estado y descripción.
3. El sistema valida que los campos obligatorios estén completos, que la cantidad sea mayor que cero y que el estado sea válido: disponible, no disponible o en mantenimiento. Ante un error, lo informa y no guarda.
4. Si los datos son válidos, crea el recurso asociado al negocio, lo agrega al listado y confirma el registro.
5. El recurso queda disponible para relacionarlo posteriormente con servicios y empleados.

<p align="center">
  <img src="https://raw.githubusercontent.com/hawrisson350/EBP07-API-2026-2-BookingNow/main/docs/wiki/media/sprint-2/HU-040101-registrar-recurso.png" alt="Diagrama de actividades para registrar un recurso" width="900">
</p>

### HU-030101 — Listar información de servicio

**Como cliente**, quiero consultar la información detallada de un servicio seleccionado, para conocer sus características antes de realizar una reserva.

**Flujo descrito:**

1. El cliente selecciona un servicio desde los resultados de búsqueda o desde el listado.
2. El sistema consulta la información del servicio y del negocio y muestra una ficha con nombre, categoría, duración, precio, descripción y negocio.
3. Si el servicio tiene imágenes, muestra una imagen o un carrusel, según corresponda. Si no tiene imágenes, continúa sin mostrarlas.
4. El sistema ofrece la opción **Reservar servicio**. Si el cliente la elige, inicia el proceso de reserva; de lo contrario, termina la consulta.

<p align="center">
  <img src="https://raw.githubusercontent.com/hawrisson350/EBP07-API-2026-2-BookingNow/main/docs/wiki/media/sprint-2/HU-030101-consultar-servicio.png" alt="Diagrama de actividades para consultar la información de un servicio" width="900">
</p>

### HU-050101 — Registrar empleado

**Como proveedor**, quiero registrar los empleados de mi negocio indicando sus datos personales y de contacto, para tenerlos disponibles en el sistema y relacionarlos posteriormente con los servicios y recursos correspondientes.

**Flujo descrito:**

1. El proveedor selecciona el negocio y elige **Registrar empleado**.
2. El sistema muestra un formulario con nombres, documento de identidad, teléfono y correo.
3. Valida que los campos obligatorios estén completos y que el documento y el correo tengan formatos válidos.
4. Consulta los empleados del negocio y verifica el límite permitido por el plan y que el documento no esté ya registrado en ese negocio.
5. Si los datos son válidos, crea el empleado asociado al negocio, lo agrega al listado y confirma el registro.
6. El empleado queda disponible para asignarlo a servicios y recursos.

<p align="center">
  <img src="https://raw.githubusercontent.com/hawrisson350/EBP07-API-2026-2-BookingNow/main/docs/wiki/media/sprint-2/HU-050101-registrar-empleado.png" alt="Diagrama de actividades para registrar un empleado" width="900">
</p>

### HU-030202 — Asignar empleados y recursos a un servicio

**Como proveedor**, quiero asignar empleados y recursos a los servicios de mi negocio, para definir qué personal y recursos pueden utilizarse para realizar cada servicio y facilitar la gestión de las reservas.

**Flujo descrito:**

1. El proveedor selecciona un servicio de su negocio y elige **Asignar empleados y recursos**. El sistema muestra las asignaciones actuales.
2. El proveedor define si el servicio requiere empleados, recursos o ambos.
3. Si requiere empleados, el sistema muestra los empleados del mismo negocio y permite agregar o quitar uno o varios.
4. Si requiere recursos, el sistema muestra los recursos del mismo negocio y permite agregar o quitar uno o varios.
5. El sistema muestra un resumen para confirmar. Si hay asignaciones duplicadas, informa el error; si no, guarda y muestra las asignaciones del servicio.

<p align="center">
  <img src="https://raw.githubusercontent.com/hawrisson350/EBP07-API-2026-2-BookingNow/main/docs/wiki/media/sprint-2/HU-030202-asignar-empleados-recursos.png" alt="Diagrama de actividades para asignar empleados y recursos a un servicio" width="900">
</p>

### HU-020202 — Configurar horario de atención del negocio

**Como proveedor**, quiero configurar el horario de atención de mi negocio durante su registro, indicando los días y horas en los que estará disponible, para que el sistema pueda conocer su disponibilidad y utilizarla posteriormente en la gestión de servicios y reservas.

**Flujo descrito:**

1. Al registrar el negocio, el proveedor encuentra la sección **Horario de atención**.
2. Marca los días de atención e indica la hora de apertura y cierre de cada día seleccionado.
3. El sistema valida en el servidor que cada día seleccionado tenga ambas horas y que la hora de cierre sea posterior a la de apertura.
4. Si el horario es válido, guarda el negocio con su horario, muestra la confirmación y deja el horario visible en la información del negocio.

<p align="center">
  <img src="https://raw.githubusercontent.com/hawrisson350/EBP07-API-2026-2-BookingNow/main/docs/wiki/media/sprint-2/HU-020202-horario-negocio.png" alt="Diagrama de actividades para configurar el horario de atención del negocio" width="900">
</p>

## Arquitectura seleccionada

El equipo eligió una arquitectura que ubica las dependencias técnicas en los bordes de la aplicación y mantiene las decisiones del dominio en el núcleo. La intención es definir límites claros y conservar la trazabilidad entre historias de usuario, casos de uso y adaptadores; la arquitectura no elimina por sí sola todos los problemas del proyecto.

### Aplicación en BookingNow

El backend se organiza en tres zonas:

| Zona | Responsabilidad | Ejemplos |
|---|---|---|
| Dominio | Representa reglas y conceptos del negocio sin depender de HTTP ni JPA. | Cliente, Proveedor, Negocio, Servicio, Reserva, Recurso y Empleado. |
| Aplicación | Coordina los casos de uso mediante puertos de entrada y salida. | ReservaService, ServicioService, puertos de casos de uso y repositorios. |
| Infraestructura | Traduce solicitudes HTTP y conecta dependencias tecnológicas. | Controladores REST, adaptadores JPA, JWT, BCrypt y Swagger. |

### Ejemplo de recorrido

Al registrar una reserva, `ReservaController` recibe la solicitud HTTP y la entrega a `RegistrarReservaUseCase`. `ReservaService` coordina las reglas y consulta los puertos de salida para acceder a los datos. Las implementaciones JPA están en infraestructura, así que el caso de uso no depende directamente del controlador ni de una tecnología concreta de base de datos.

### Beneficios esperados y límites actuales

La separación por responsabilidades permite que cambios en la API REST o la persistencia se concentren principalmente en sus adaptadores. También facilita revisar y probar las reglas de negocio. El documento fuente presenta estos puntos como beneficios esperados, no como una garantía de que todo cambio quede aislado.

## Diagrama de componentes

<p align="center">
  <img src="https://raw.githubusercontent.com/hawrisson350/EBP07-API-2026-2-BookingNow/main/docs/wiki/media/sprint-2/diagrama-componentes.png" alt="Diagrama de componentes de BookingNow" width="900">
</p>

## Diagrama de despliegue

<p align="center">
  <img src="https://raw.githubusercontent.com/hawrisson350/EBP07-API-2026-2-BookingNow/main/docs/wiki/media/sprint-2/diagrama-despliegue.png" alt="Diagrama de despliegue de BookingNow" width="900">
</p>

## Modelo C4

### Nivel 1 — Contexto

<p align="center">
  <img src="https://raw.githubusercontent.com/hawrisson350/EBP07-API-2026-2-BookingNow/main/docs/wiki/media/sprint-2/c4-nivel-1-contexto.png" alt="C4 nivel 1: contexto de BookingNow" width="900">
</p>

### Nivel 2 — Contenedores

<p align="center">
  <img src="https://raw.githubusercontent.com/hawrisson350/EBP07-API-2026-2-BookingNow/main/docs/wiki/media/sprint-2/c4-nivel-2-contenedores.png" alt="C4 nivel 2: contenedores de BookingNow" width="900">
</p>

### Nivel 3 — Componentes

<p align="center">
  <img src="https://raw.githubusercontent.com/hawrisson350/EBP07-API-2026-2-BookingNow/main/docs/wiki/media/sprint-2/c4-nivel-3-componentes.png" alt="C4 nivel 3: componentes de BookingNow" width="900">
</p>

## Aplicación de principios SOLID en el backend

El backend combina arquitectura hexagonal con decisiones de diseño relacionadas con SOLID. Este análisis describe su aplicación a partir del código y señala también sus límites.

### Arquitectura y flujo de una solicitud

En el registro de clientes, `ClienteController` recibe la solicitud HTTP y delega la acción en `CrearClienteUseCase`. `ClienteService` aplica las reglas y solicita guardar mediante `ClienteRepositoryPort`; `ClienteRepositoryAdapter` implementa ese puerto y utiliza Spring Data JPA.

### S — Responsabilidad única

El transporte HTTP, las reglas de aplicación y la persistencia están en componentes distintos: `ClienteController`, `ClienteService` y `ClienteRepositoryAdapter`. Sin embargo, `ClienteService` reúne varios casos de uso de cliente, por lo que la separación no es estricta dentro de esa clase.

### O — Abierto a extensión y cerrado a modificación

`ClienteRepositoryPort` y `ContrasenaPort` aíslan la aplicación de los mecanismos concretos. Sus adaptadores implementan esos contratos, lo que deja puntos para incorporar otras estrategias. El documento fuente no presenta implementaciones alternativas en producción.

### L — Sustitución de Liskov

Una implementación alternativa de `ClienteRepositoryPort` podría sustituir al adaptador JPA si conserva el comportamiento esperado. No se identificaron varias implementaciones del puerto ni pruebas de contrato que demuestren esa sustitución. Cliente, Proveedor y Usuario son modelos independientes; no se usa herencia como evidencia de este principio.

### I — Segregación de interfaces

Los puertos de entrada se definen por operación, como `CrearClienteUseCase`, `ObtenerClienteUseCase` y `EliminarClienteUseCase`. Los puertos de salida también separan capacidades. Algunos servicios implementan varias interfaces de entrada para agrupar casos de uso de una entidad.

### D — Inversión de dependencias

`ClienteController` depende del caso de uso de entrada; `ClienteService` recibe puertos de salida; y los adaptadores de infraestructura implementan esos contratos. Spring conecta las implementaciones mediante inyección de dependencias. Las reglas de registro y autenticación no conocen directamente Spring Data JPA ni BCrypt.

La evidencia más directa está en la inversión de dependencias y la segregación de interfaces. La separación por capas favorece la responsabilidad única, aunque algunos servicios agrupen casos de uso. Para Liskov y la extensión, la evidencia disponible es principalmente estructural.

### Referencias del código

Desde `src/main/java/co/edu/udea/bookingnow/`:

- `infrastructure/adapter/in/rest/ClienteController.java`
- `application/service/ClienteService.java`
- `application/port/in/cliente/CrearClienteUseCase.java`
- `application/port/out/ClienteRepositoryPort.java`
- `infrastructure/adapter/out/persistence/ClienteRepositoryAdapter.java`
- `application/port/out/ContrasenaPort.java`
- `infrastructure/adapter/out/security/ContrasenaAdapter.java`

## Evidencias y anexos

- [Diagramas C4](https://drive.google.com/drive/folders/1WzoiaE7qIBTVw_674neUozQnOfKdtJbR?usp=drive_link)
- [Diagrama de actividades de usuario](https://drive.google.com/drive/folders/1hkDQ9VvJl6hdExAQIHY6rHbbKDi_HsUo?usp=drive_link)
- [Diagrama de componentes](https://drive.google.com/file/d/1tB2B92ZonU3u-rwzJ4DtDsmqv1M6FdPX/view?usp=drive_link)
- [Diagrama de despliegue](https://drive.google.com/file/d/194Y4Wz8x71PcmiFVE7prPX2MLXEc1QVd/view?usp=drive_link)
- [Diagrama de clases corregido](https://drive.google.com/file/d/1PEa-HphaGITqoN5eW0Hm2-mCn7HGxF4G/view?usp=drive_link)
- [BPM](https://drive.google.com/file/d/1mijnK6MFBwKTPIwXtRAUQjqCpEkh6oWO/view?usp=drive_link)
- [Repositorio del frontend](https://github.com/luisayarurob/BookingNow-)
- [Repositorio del backend](https://github.com/hawrisson350/EBP07-API-2026-2-BookingNow)
- [Video de muestra](https://youtu.be/vbiEnj6a2rg)

---

[← Volver al documento general](Sistema_de_gestion_BookingNow.md) · [Volver a Sprint 1](Sprint_1.md)
