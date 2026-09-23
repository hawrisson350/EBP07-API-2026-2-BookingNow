# Sprint 1

> [← Volver al documento general del proyecto](Sistema_de_gestion_BookingNow.md)


## Objetivo

El primer sprint tiene como objetivo establecer la infraestructura base
del sistema mediante la implementación del registro y autenticación de
usuarios según sus roles, así como el registro inicial de negocios y
servicios, garantizando el almacenamiento de la información y la
seguridad de la misma.

## Modelo verbal

### Descripción del problema

Se identificó la falta de herramientas que permitan a negocios como
clínicas, centros deportivos, salones de belleza y consultorios
organizar de forma adecuada sus horarios, recursos, empleados y
reportes. Esta carencia genera sobreocupación y desorden en las
cancelaciones, afectando directamente la gestión de sus agendas y la
calidad del servicio ofrecido .

### Objetivo del sistema

Con el fin de dar solución a esta problemática se desarrolló un sistema
que permite a clientes y proveedores registrarse y acceder a interfaces
diferenciadas según su rol, facilitando así la reservación y cancelación
de servicios, la gestión de agendas y la consulta del historial de
reservas. A los proveedores, el sistema les brinda la posibilidad de
registrar su negocio y gestionar sus empleados, recursos, servicios,
agendas y reportes de forma centralizada.

### Alcance del sprint

Para el primer sprint se espera Implementar las funcionalidades base de
registro y autenticación de usuarios ; clientes y proveedores, así como
el registro inicial de negocios y servicios, teniendo en cuenta la
seguridad y escalabilidad el sistema sobre el cual se trabajará el los
posteriores sprint

Incluye:

**Backend:** lógica de autenticación,validaciones y el guardado en la
base de datos de los registros de clientes, proveedores, negocios y
servicios.

**Frontend:** pantallas de registro de cliente, registro de proveedor,
inicio de sesión, registro de negocio y registro de servicio.

No incluye:

- Gestión de recursos, disponibilidad ni agendas.

- Gestión de empleados ni asignación a servicios.

- Registro, modificación o cancelación de reservas.

- Notificaciones (confirmación, recordatorio, cancelación).

- Reportes o análisis de ocupación/uso.

## Story Mapping

### Actividades del usuario

El proceso inicia cuando el usuario accede a la plataforma. En este
punto, el sistema determina si el usuario cuenta con una cuenta
registrada. En caso de no tenerla, el usuario debe registrarse
seleccionando su rol: si se registra como cliente, debe ingresar su
correo, nombre de usuario y contraseña; si se registra como proveedor,
debe proporcionar su correo, razón social, NIT, usuario y contraseña.
Si, por el contrario, el usuario ya cuenta con una cuenta, procede a
iniciar sesión con su usuario y contraseña, teniendo además la
posibilidad de recuperar su contraseña mediante un código en caso de
haberla olvidado. Una vez autenticado, el sistema identifica el rol de
la cuenta para dirigir al usuario hacia el flujo correspondiente, ya sea
como cliente o como proveedor.

Dentro del flujo del cliente, el usuario tiene la posibilidad de
actualizar su información personal en cualquier momento. Asimismo, puede
buscar negocios o servicios de su interés: en el caso de los negocios,
puede buscarlos por nombre y listar su información; en el caso de los
servicios, puede realizar la misma búsqueda por nombre y consultar su
información detallada. En cuanto a la gestión de reservas, el cliente
puede registrar una nueva reserva indicando la fecha y hora deseadas, lo
cual genera automáticamente la actualización de la disponibilidad del
recurso correspondiente. También puede consultar el historial de sus
reservas, actualizar la fecha y hora de una reserva existente, o
cancelarla, acción que a su vez libera el recurso previamente asignado.
A lo largo de este proceso, el cliente recibe notificaciones
automáticas: una confirmación al registrar la reserva, un recordatorio
previo a la fecha programada y una notificación en caso de cancelación.
Finalmente, el cliente puede optar por cerrar sesión y salir de la
plataforma.

Para el proveedor, el sistema verifica primero si este cuenta con un
negocio registrado. Si no lo tiene, debe registrarlo proporcionando el
nombre, correo, número de contacto, dirección, categoría, foto principal
y galería multimedia del negocio. Si ya cuenta con uno, puede editarlo,
activarlo o desactivarlo, así como visualizar su historial. De manera
similar, en cuanto a los servicios, si el proveedor no tiene un servicio
registrado debe crearlo indicando el negocio asociado, el nombre, la
imagen de referencia, la duración, el precio y la descripción del
servicio; si ya cuenta con uno, puede editarlo, activarlo o
desactivarlo, y consultar su historial.

En la gestión de recursos, el proveedor puede registrar un nuevo recurso
indicando su nombre y tipo, e ingresar la cantidad disponible, para
luego asociarlo a un servicio específico. Además, puede consultar los
recursos registrados, ver sus detalles, verificar su disponibilidad,
actualizarla, liberar un recurso cuando corresponda, y buscar recursos
puntuales.

En cuanto a la gestión de empleados, el proveedor puede registrar nuevos
empleados y asignarlos a los servicios que ofrece. También puede
consultar la lista de empleados, ver el detalle de cada uno y realizar
búsquedas específicas. Tiene la posibilidad de activar o desactivar
empleados; en el caso de la desactivación, el sistema guarda
automáticamente un historial de empleados inactivos. Adicionalmente, el
proveedor puede definir y modificar los horarios de sus empleados, así
como verificar su disponibilidad para la asignación a reservas.

Finalmente, el proveedor cuenta con herramientas de consulta y análisis:
puede consultar y filtrar las reservas registradas, analizar la
ocupación y el uso de los servicios, analizar las cancelaciones
ocurridas, y exportar reportes con esta información. Al concluir sus
actividades, el proveedor también tiene la opción de cerrar sesión y
finalizar su interacción con la plataforma.

### Historias asociadas

Para mejor visualización del mapa correspondiente a la historia de
usuario que se trabajarán por sprint acceder al anexo 1.

<p align="center">
  <img src="https://raw.githubusercontent.com/hawrisson350/EBP07-API-2026-2-BookingNow/main/docs/wiki/media/media/image13.jpg" alt="User story map del proyecto" width="900">
</p>

**HU-10: Registrar negocio**

Como proveedor quiero registrar mis negocios indicando su información,
para publicar y gestionar mis servicios en la plataforma.

**Criterios de aceptación:**

-   **Escenario 1: Opción de registro disponible.**

    -   Dado que estoy autenticado con perfil de proveedor y no tengo un
        > negocio registrado en mi cuenta

    -   Cuando accedo a la plataforma

    -   Entonces el sistema muestra la opción "Registrar negocio" y me
        > permite acceder al formulario de registro.

-   **Escenario 2: Opción de registro restringida por negocio ya
    > registrado.**

    -   Dado que estoy autenticado con perfil de proveedor y ya tengo un
        > negocio registrado en mi cuenta

    -   Cuando accede al formulario de registro e ingreso los datos de
        > un negocio agregado anteriormente.

    -   Entonces el sistema notifica "Este negocio ya se encuentra
        > registrado" y bloquea el acceso al formulario y me da la
        > opción de volver a registrar.

-   **Escenario 3: Creación exitosa de un negocio presencial.**

    -   Dado que estoy autenticado con perfil de proveedor y me
        > encuentro en el formulario de "Registrar negocio"

    -   Cuando ingreso el nombre del negocio, un correo electrónico con
        > formato usuario@dominio.extensión, un número de contacto
        > válido, la dirección, selecciono una categoría, subo la foto
        > principal (jpg/png) y la galería de multimedia y doy clic en
        > el botón "Registrar negocio"

    -   Entonces el sistema crea el negocio, lo asocia a mi cuenta y
        > muestra el mensaje de confirmación "Negocio creado
        > exitosamente".

-   **Escenario 4: Creación exitosa de un negocio virtual.**

    -   Dado que estoy autenticado con perfil de proveedor y me
        > encuentro en el formulario de "Registrar negocio"

    -   Cuando indico que el negocio ofrece sus servicios de manera
        > virtual, dejo el campo de la dirección vacío, diligencio el
        > resto de la información obligatoria con formato válido y doy
        > clic en el botón "Registrar negocio"

    -   Entonces el sistema crea el negocio sin exigir una dirección
        > física, lo asocia a mi cuenta y muestra el mensaje de
        > confirmación "Negocio creado exitosamente".

-   **Escenario 5: Intento de registro con campos obligatorios sin
    > diligenciar.**

    -   Dado que estoy autenticado con perfil de proveedor y me
        > encuentro en el formulario de "Registrar negocio"

    -   Cuando dejo vacío uno o más campos obligatorios (nombre del
        > negocio, correo electrónico, número de contacto, categoría,
        > foto principal o dirección en un negocio presencial) y doy
        > clic en el botón "Registrar negocio"

    -   Entonces el sistema bloquea el registro y muestra el mensaje de
        > error "Debes diligenciar los campos obligatorios pendientes",
        > resaltando cada campo faltante.

-   **Escenario 6: Intento de registro con correo electrónico
    > inválido.**

    -   Dado que estoy autenticado con perfil de proveedor y me
        > encuentro en el formulario de "Registrar negocio"

    -   Cuando ingreso un correo electrónico con formato incorrecto (ej.
        > "negocio.com"), diligencio el resto de la información con
        > formato válido y doy clic en el botón "Registrar negocio"

    -   Entonces el sistema bloquea el registro y muestra el mensaje de
        > error "El correo electrónico no es válido", solicitando
        > corregirlo.

-   **Escenario 7: Intento de registro con número de contacto
    > inválido.**

    -   Dado que estoy autenticado con perfil de proveedor y me
        > encuentro en el formulario de "Registrar negocio"

    -   Cuando ingreso un número de contacto con formato incorrecto (ej.
        > "abc123"), diligencio el resto de la información con formato
        > válido y doy clic en el botón "Registrar negocio"

    -   Entonces el sistema bloquea el registro y muestra el mensaje de
        > error "El número de contacto no es válido", solicitando
        > corregirlo.

-   **Escenario 8: Intento de registro con foto principal en formato no
    > permitido.**

    -   Dado que estoy autenticado con perfil de proveedor y me
        > encuentro en el formulario de "Registrar negocio"

    -   Cuando subo como foto principal un archivo distinto a jpg o png,
        > diligencio el resto de la información con formato válido y doy
        > clic en el botón "Registrar negocio"

    -   Entonces el sistema bloquea el registro y muestra el mensaje de
        > error "La foto principal debe estar en formato jpg o png".

**HU-06: Registrar cliente con correo y contraseña**

Como usuario, quiero registrarme con un correo y contraseña, para crear
una cuenta en el sistema.

**Criterios de aceptación:**

-   **Escenario 1: Registro exitoso.**

    -   Dado que no tengo una cuenta en la aplicación

    -   Cuando ingreso un correo electrónico con formato
        > usuario@dominio.extensión que no está registrado previamente,
        > un nombre de usuario con mínimo 3 caracteres y una contraseña
        > segura con mínimo 8 caracteres, con al menos una letra, un
        > número y un carácter especial

    -   Entonces el sistema crea la cuenta exitosamente y muestra el
        > mensaje de confirmación "Cuenta creada exitosamente".

-   **Escenario 2: Correo inválido.**

    -   Dado que el usuario ingresa un correo con formato incorrecto

    -   Cuando intenta registrarse

    -   Entonces el sistema deniega la creación de la cuenta y muestra
        > el mensaje de error "El correo no es válido".

-   **Escenario 3: Contraseña inválida.**

    -   Dado que el usuario ingresa una contraseña con formato
        > incorrecto

    -   Cuando intenta registrarse

    -   Entonces el sistema deniega la creación de la cuenta y muestra
        > el mensaje de error "La contraseña no es válida".

-   **Escenario 4: Nombre de usuario inválido.**

    -   Dado que el usuario ingresa un nombre de usuario con formato
        > incorrecto

    -   Cuando intenta registrarse

    -   Entonces el sistema deniega la creación de la cuenta y muestra
        > el mensaje de error "El nombre de usuario no es válido".

-   **Escenario 5: Correo ya registrado.**

    -   Dado que el usuario ingresa un correo ya registrado en la
        > plataforma

    -   Cuando intenta registrarse

    -   Entonces el sistema deniega la creación de la cuenta y muestra
        > el mensaje de error "El correo ya está en uso".

**HU-07: Registrar proveedor con razón social y NIT**

Como usuario, quiero registrarme con razón social y NIT de mi empresa,
para crear una cuenta en el sistema.

**Criterios de aceptación:**

-   **Escenario 1: Registro exitoso.**

    -   Dado que no tengo una cuenta en la aplicación

    -   Cuando ingreso un correo electrónico con formato
        > usuario@dominio.extensión, una razón social con mínimo 3
        > caracteres, un NIT con formato #########-# (9 dígitos, un
        > guion y un dígito de verificación), un nombre de usuario con
        > mínimo 3 caracteres y una contraseña segura con mínimo 8
        > caracteres, con al menos una letra, un número y un carácter
        > especial, y el correo, el NIT y la razón social no están
        > registrados previamente

    -   Entonces el sistema crea la cuenta exitosamente y muestra el
        > mensaje de confirmación "Cuenta creada exitosamente".

-   **Escenario 2: Correo inválido.**

    -   Dado que el usuario ingresa un correo con formato incorrecto

    -   Cuando intenta registrarse

    -   Entonces el sistema deniega la creación de la cuenta y muestra
        > el mensaje de error "El correo no es válido".

-   **Escenario 3: Razón social inválida.**

    -   Dado que el usuario ingresa una razón social con formato
        > incorrecto

    -   Cuando intenta registrarse

    -   Entonces el sistema deniega la creación de la cuenta y muestra
        > el mensaje de error "La razón social no es válida".

-   **Escenario 4: NIT inválido.**

    -   Dado que el usuario ingresa un NIT con formato incorrecto

    -   Cuando intenta registrarse

    -   Entonces el sistema deniega la creación de la cuenta y muestra
        > el mensaje de error "El NIT no es válido".

-   **Escenario 5: Contraseña inválida.**

    -   Dado que el usuario ingresa una contraseña con formato
        > incorrecto

    -   Cuando intenta registrarse

    -   Entonces el sistema deniega la creación de la cuenta y muestra
        > el mensaje de error "La contraseña no es válida".

-   **Escenario 6: Nombre de usuario inválido.**

    -   Dado que el usuario ingresa un nombre de usuario con formato
        > incorrecto

    -   Cuando intenta registrarse

    -   Entonces el sistema deniega la creación de la cuenta y muestra
        > el mensaje de error "El nombre de usuario no es válido".

-   **Escenario 7: Correo ya registrado.**

    -   Dado que el usuario ingresa un correo ya registrado en la
        > plataforma

    -   Cuando intenta registrarse

    -   Entonces el sistema deniega la creación de la cuenta.

-   **Escenario 8: Razón social ya registrada.**

    -   Dado que el usuario ingresa una razón social ya registrada en la
        > plataforma

    -   Cuando intenta registrarse

    -   Entonces el sistema deniega la creación de la cuenta.

-   **Escenario 9: NIT ya registrado.**

    -   Dado que el usuario ingresa un NIT ya registrado en la
        > plataforma

    -   Cuando intenta registrarse

    -   Entonces el sistema deniega la creación de la cuenta.

**HU-09: Iniciar sesión con correo y contraseña**

Como usuario, quiero iniciar sesión con mi correo y contraseña mediante
una cuenta previamente registrada, para acceder a mi información
personal en el sistema.

**Criterios de aceptación:**

-   **Escenario 1: Inicio de sesión con credenciales correctas.**

    -   Dado que el usuario tiene una cuenta previamente registrada y
        > activa

    -   Cuando ingresa correctamente su correo y contraseña y selecciona
        > "Iniciar sesión"

    -   Entonces el sistema le permite el acceso a la plataforma y
        > muestra las funcionalidades correspondientes a su cuenta.

-   **Escenario 2: Contraseña incorrecta.**

    -   Dado que el usuario ingresa un correo electrónico registrado y
        > una contraseña incorrecta

    -   Cuando selecciona "Iniciar sesión"

    -   Entonces el sistema impide el acceso y muestra el mensaje de
        > error "Correo o contraseña incorrectos".

-   **Escenario 3: Correo no registrado.**

    -   Dado que el usuario ingresa un correo electrónico que no está
        > asociado a una cuenta registrada

    -   Cuando selecciona "Iniciar sesión"

    -   Entonces el sistema impide el acceso y muestra el mensaje de
        > error "Correo o contraseña incorrectos", sin indicar si el
        > correo está registrado o no.

-   **Escenario 4: Campos obligatorios.**

    -   Dado que el usuario se encuentra en la pantalla de inicio de
        > sesión

    -   Cuando selecciona "iniciar sesión" dejando vacío el correo, la
        > contraseña o ambos campos

    -   Entonces el sistema indica los campos que están incompletos y no
        > permite el inicio de sesión hasta que sean diligenciados.

-   **Escenario 5: Correo con formato incorrecto.**

    -   Dado que el usuario ingresa un correo electrónico con un formato
        > inválido

    -   Cuando selecciona "Iniciar sesión"

    -   Entonces el sistema no permite el acceso y muestra el mensaje de
        > error "Debes ingresar un correo electrónico válido".

-   **Escenario 6: Identificación del rol del usuario.**

    -   Dado que el usuario ingresa credenciales válidas
        > correspondientes a una cuenta registrada

    -   Cuando el sistema valida la información

    -   Entonces el sistema identifica el rol asociado a la cuenta, como
        > cliente o proveedor, y permite el acceso de acuerdo con dicho
        > rol.

-   **Escenario 7: Acceso según el rol.**

    -   Dado que el usuario ha iniciado sesión correctamente y su rol ha
        > sido identificado

    -   Cuando ingresa a la plataforma

    -   Entonces visualiza las opciones y funcionalidades
        > correspondientes a su rol y no tiene acceso a funcionalidades
        > que correspondan exclusivamente a otros roles.

-   **Escenario 8: Cuenta inactiva o bloqueada.**

    -   Dado que el usuario ingresa correctamente su correo y
        > contraseña, pero su cuenta se encuentra inactiva o bloqueada

    -   Cuando selecciona "Iniciar sesión"

    -   Entonces el sistema impide el acceso y muestra el mensaje "La
        > cuenta no se encuentra habilitada para iniciar sesión".

-   **Escenario 9: Protección de la contraseña.**

    -   Dado que el usuario se encuentra ingresando su contraseña

    -   Cuando escribe los caracteres en el campo correspondiente

    -   Entonces el sistema oculta los caracteres de la contraseña para
        > evitar que otras personas puedan visualizarla.

-   **Escenario 10: Tiempo de respuesta.**

    -   Dado que el usuario ha ingresado correctamente su correo y
        > contraseña

    -   Cuando selecciona "Iniciar sesión"

    -   Entonces el sistema muestra el resultado del inicio de sesión en
        > un tiempo máximo de 3 segundos, siempre que exista conexión
        > disponible con la plataforma.

**HU-13: Registrar servicio**

Como proveedor, quiero registrar un servicio asociado a uno de mis
negocios, indicando su nombre, imagen de referencia, duración, precio y
descripción, para tenerlo disponible en el sistema.

**Criterios de aceptación:**

-   **Escenario 1: Negocio asociado.**

    -   Dado que el proveedor se encuentra en el formulario de registro
        > de servicio

    -   Cuando selecciona "Registrar"

    -   Entonces el sistema le permite seleccionar uno de sus negocios
        > para asociar el servicio.

-   **Escenario 2: Datos del servicio.**

    -   Dado que el proveedor se encuentra en el formulario de registro
        > de servicio

    -   Cuando accede al formulario

    -   Entonces el sistema solicita el nombre, la duración, el precio y
        > la descripción del servicio.

-   **Escenario 3: Imagen de referencia.**

    -   Dado que el proveedor se encuentra en el formulario de registro
        > de servicio

    -   Cuando desea agregar una imagen de referencia del servicio

    -   Entonces el sistema le permite agregarla.

-   **Escenario 4: Duración válida.**

    -   Dado que el proveedor se encuentra en el formulario de registro
        > de servicio

    -   Cuando ingresa la duración del servicio

    -   Entonces el sistema acepta únicamente valores mayores a 0
        > minutos y rechaza cualquier otro valor con el mensaje de error
        > "La duración debe ser mayor a 0 minutos".

-   **Escenario 5: Precio válido.**

    -   Dado que el proveedor se encuentra en el formulario de registro
        > de servicio

    -   Cuando ingresa el precio del servicio

    -   Entonces el sistema acepta valores iguales o mayores a 0 y
        > rechaza los valores negativos con el mensaje de error "El
        > precio no puede ser negativo".

-   **Escenario 6: Validación de información.**

    -   Dado que falta algún dato obligatorio o el proveedor ingresa
        > información inválida

    -   Cuando intenta guardar el servicio

    -   Entonces el sistema muestra un mensaje indicando el error y no
        > permite guardar el servicio.

-   **Escenario 7: Asociación con el proveedor.**

    -   Dado que el proveedor seleccionó un negocio y registró
        > correctamente la información del servicio

    -   Cuando guarda el servicio

    -   Entonces el servicio queda asociado al negocio seleccionado y,
        > por medio de este, al proveedor que lo registró.

-   **Escenario 8: Disponibilidad del servicio.**

    -   Dado que el servicio fue registrado correctamente

    -   Cuando el sistema finaliza el registro

    -   Entonces el servicio aparece en el listado de servicios del
        > negocio y queda disponible para utilizarse en las reservas.

## Mockups

### Página de inicio

Al abrir la aplicación, el usuario encuentra la página de inicio, desde donde
puede iniciar sesión o registrarse según cuente o no con una cuenta creada.

### Flujo de cliente

Al ingresar como cliente sin una cuenta previa, puede completar el registro y
recibe la confirmación de creación. En los sprints posteriores, esta área
permitirá buscar negocios y sus servicios.

| Inicio y registro | Confirmación |
|---|---|
| <img src="https://raw.githubusercontent.com/hawrisson350/EBP07-API-2026-2-BookingNow/main/docs/wiki/media/media/image10.png" alt="Página de inicio" width="420"> | <img src="https://raw.githubusercontent.com/hawrisson350/EBP07-API-2026-2-BookingNow/main/docs/wiki/media/media/image9.png" alt="Registro de cliente" width="420"> |
| <img src="https://raw.githubusercontent.com/hawrisson350/EBP07-API-2026-2-BookingNow/main/docs/wiki/media/media/image4.png" alt="Formulario de cliente" width="420"> | <img src="https://raw.githubusercontent.com/hawrisson350/EBP07-API-2026-2-BookingNow/main/docs/wiki/media/media/image3.png" alt="Confirmación de registro de cliente" width="420"> |

### Flujo de proveedor

El proveedor puede crear una cuenta, iniciar sesión y registrar su negocio con
los servicios asociados. Cada paso muestra su confirmación correspondiente.

| Acceso y registro | Registro de negocio |
|---|---|
| <img src="https://raw.githubusercontent.com/hawrisson350/EBP07-API-2026-2-BookingNow/main/docs/wiki/media/media/image10.png" alt="Página de inicio" width="420"> | <img src="https://raw.githubusercontent.com/hawrisson350/EBP07-API-2026-2-BookingNow/main/docs/wiki/media/media/image8.png" alt="Registro de proveedor" width="420"> |
| <img src="https://raw.githubusercontent.com/hawrisson350/EBP07-API-2026-2-BookingNow/main/docs/wiki/media/media/image2.png" alt="Formulario de proveedor" width="420"> | <img src="https://raw.githubusercontent.com/hawrisson350/EBP07-API-2026-2-BookingNow/main/docs/wiki/media/media/image1.png" alt="Registro de negocio" width="420"> |
| <img src="https://raw.githubusercontent.com/hawrisson350/EBP07-API-2026-2-BookingNow/main/docs/wiki/media/media/image6.png" alt="Registro de servicio" width="420"> | <img src="https://raw.githubusercontent.com/hawrisson350/EBP07-API-2026-2-BookingNow/main/docs/wiki/media/media/image5.png" alt="Confirmación de servicio" width="420"> |

## Modelo de Dominio

<p align="center">
  <img src="https://raw.githubusercontent.com/hawrisson350/EBP07-API-2026-2-BookingNow/main/docs/wiki/media/media/image12.jpg" alt="Modelo de dominio de BookingNow" width="900">
</p>

## Diagrama de Clases

<p align="center">
  <img src="https://raw.githubusercontent.com/hawrisson350/EBP07-API-2026-2-BookingNow/main/docs/wiki/media/media/image7.png" alt="Diagrama de clases" width="900">
</p>
> El diagrama completo se encuentra en el Anexo 2.

## BPMN

> Para consultar el modelo completo, ver el Anexo 3.

<p align="center">
  <img src="https://raw.githubusercontent.com/hawrisson350/EBP07-API-2026-2-BookingNow/main/docs/wiki/media/media/image11.jpg" alt="Diagrama BPMN" width="900">
</p>

## Conclusiones del sprint

Durante el desarrollo del Sprint 1 se logró establecer la base funcional
del sistema de gestión de reservas de servicios, enfocándose en los
procesos de registro y autenticación de usuarios, así como en el
registro inicial de negocios y servicios por parte de los proveedores.

En el backend, se completó satisfactoriamente la implementación de las
funcionalidades correspondientes al registro de clientes y proveedores,
la autenticación mediante correo y el registro de negocios y servicios
con su respectiva persistencia en base de datos, cumpliendo así con los
criterios de aceptación definidos para cada historia de usuario.

En cuanto al frontend, se avanzó en el diseño y validación de los
mockups correspondientes a la página de inicio, el flujo de registro e
inicio de sesión del cliente, y el flujo de registro de cuenta, negocio
y servicios del proveedor, sentando las bases visuales que guiarán la
implementación de las interfaces en las próximas iteraciones.

Este sprint permitió validar que la arquitectura y el flujo de
navegación propuestos son coherentes con las necesidades identificadas
en la problemática inicial, además de dejar consolidada la
infraestructura de seguridad y persistencia sobre la cual se construirán
los módulos de recursos, empleados, reservas y reportes en los sprints
siguientes.

Como puntos de mejora para el próximo sprint, se identifica la necesidad
de asignar oportunamente los responsables de las tareas de frontend, con
el fin de evitar cuellos de botella entre el avance del backend y su
correspondiente implementación visual, así como iniciar la integración
entre ambos componentes para contar con un flujo funcional de principio
a fin.

## Evidencias y anexos

### Anexos

Anexo 1. user story map

[https://miro.com/app/live-embed/uXjVHkaOpSQ=/?focusWidget=3458764684522661136&embedMode=view_only_without_ui&embedId=755742205466](https://miro.com/app/live-embed/uXjVHkaOpSQ=/?focusWidget=3458764684522661136&embedMode=view_only_without_ui&embedId=755742205466)

Anexo 2. Diagrama de clases

[https://drive.google.com/file/d/1CafGK2T4zMM9BA6M3qC442ANSMfXqieb/view?usp=sharing](https://drive.google.com/file/d/1CafGK2T4zMM9BA6M3qC442ANSMfXqieb/view?usp=sharing)

Anexo 3. modelo bpmn

[https://drive.google.com/file/d/1mijnK6MFBwKTPIwXtRAUQjqCpEkh6oWO/view?usp=drive_link](https://drive.google.com/file/d/1mijnK6MFBwKTPIwXtRAUQjqCpEkh6oWO/view?usp=drive_link)

### Evidencias

Github front:
[https://github.com/luisayarurob/BookingNow-.git](https://github.com/luisayarurob/BookingNow-.git)

Github Back:
[https://github.com/hawrisson350/EBP07-API-2026-2-BookingNow.git](https://github.com/hawrisson350/EBP07-API-2026-2-BookingNow.git)

Video Muestra:
[https://drive.google.com/drive/folders/1J1T90aqpo4lwiOsSJOiMmiHYmtxe1jnm?usp=drive_link](https://drive.google.com/drive/folders/1J1T90aqpo4lwiOsSJOiMmiHYmtxe1jnm?usp=drive_link)
