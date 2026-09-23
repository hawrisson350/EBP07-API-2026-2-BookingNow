# Diagrama de componentes: Cliente

> [← Volver al índice de componentes](Diagrama_componentes.md)

## Alcance

El diagrama describe únicamente el diseño interno de la entidad **Cliente**. Muestra cómo una solicitud REST llega al caso de uso y cómo el servicio se comunica con la persistencia y la seguridad mediante puertos. No representa frontend, despliegue, redes ni base de datos física.

## Diagrama

> Mermaid no ofrece de forma nativa todas las figuras UML de componentes, como la pestaña de componente o el conector lollipop. Por ello, el diagrama usa estereotipos UML, figuras diferentes y dependencias punteadas para distinguir cada rol.

::: mermaid
flowchart LR
    subgraph entrada[Exterior del hexágono - entrada]
        controller["«adapter in»<br/>ClienteController<br/>/api/clientes"]
        dto[/"«DTO»<br/>RegistrarClienteCommand<br/>ClienteResponse"/]
    end

    subgraph nucleo[Hexágono - núcleo de aplicación]
        direction TB
        subgraph puertosIn[Puertos de entrada]
            crear(["«port in · interface»<br/>CrearClienteUseCase"])
            obtener(["«port in · interface»<br/>ObtenerClienteUseCase"])
            listar(["«port in · interface»<br/>ObtenerClientesUseCase"])
            eliminar(["«port in · interface»<br/>EliminarClienteUseCase"])
        end

        servicio{{"«application service»<br/>ClienteService"}}
        cliente[/"«entity»<br/>Cliente"/]
        validaciones["«domain service»<br/>ValidacionCuenta<br/>ValidacionLogin"]

        subgraph puertosOut[Puertos de salida]
            clientePort(["«port out · interface»<br/>ClienteRepositoryPort"])
            correoPort(["«port out · interface»<br/>CorreoRegistradoPort"])
            contrasenaPort(["«port out · interface»<br/>ContrasenaPort"])
        end
    end

    subgraph salida[Exterior del hexágono - salida]
        clienteAdapter["«adapter out · repository»<br/>ClienteRepositoryAdapter"]
        jpaRepository(["«repository interface»<br/>ClienteJpaRepository"])
        jpaEntity[/"«persistence entity»<br/>ClienteJpaEntity"/]
        correoAdapter["«adapter out»<br/>CorreoRegistradoAdapter"]
        correoEntity[/"«persistence entity»<br/>CorreoRegistradoJpaEntity"/]
        contrasenaAdapter["«adapter out»<br/>ContrasenaAdapter · BCrypt"]
    end

    dto -->|solicitud/respuesta HTTP| controller
    controller -. «depende de» .-> crear
    controller -. «depende de» .-> obtener
    controller -. «depende de» .-> listar
    controller -. «depende de» .-> eliminar

    servicio -. «implementa» .-> crear
    servicio -. «implementa» .-> obtener
    servicio -. «implementa» .-> listar
    servicio -. «implementa» .-> eliminar
    servicio -->|crea, consulta y elimina| cliente
    servicio -->|aplica reglas| validaciones
    servicio -. «depende de» .-> clientePort
    servicio -. «depende de» .-> correoPort
    servicio -. «depende de» .-> contrasenaPort

    clienteAdapter -. «implementa» .-> clientePort
    correoAdapter -. «implementa» .-> correoPort
    contrasenaAdapter -. «implementa» .-> contrasenaPort
    clienteAdapter -->|usa| jpaRepository
    jpaRepository -->|gestiona| jpaEntity
    clienteAdapter <-->|mapea| cliente
    correoAdapter -->|consulta| correoEntity

    classDef entry fill:#DBEAFE,stroke:#2563EB,color:#172554,stroke-width:2px
    classDef port fill:#FEF3C7,stroke:#D97706,color:#78350F,stroke-width:2px,stroke-dasharray: 5 3
    classDef service fill:#F3E8FF,stroke:#7E22CE,color:#581C87,stroke-width:3px
    classDef domain fill:#FCE7F3,stroke:#DB2777,color:#831843,stroke-width:2px
    classDef adapter fill:#DCFCE7,stroke:#16A34A,color:#14532D,stroke-width:2px
    class controller,dto entry
    class crear,obtener,listar,eliminar,clientePort,correoPort,contrasenaPort,jpaRepository port
    class servicio service
    class cliente,validaciones domain
    class clienteAdapter,jpaEntity,correoAdapter,correoEntity,contrasenaAdapter adapter
:::

## Convención UML utilizada

| Notación | Significado |
|---|---|
| Rectángulo azul | Adaptador de entrada o DTO REST. |
| Óvalo amarillo punteado | Puerto: interfaz que separa la aplicación de sus dependencias. |
| Hexágono morado | Servicio de aplicación que implementa casos de uso. |
| Paralelogramo rosado | Modelo o regla del dominio. |
| Rectángulo verde | Adaptador de salida o elemento de persistencia. |
| Flecha punteada | Dependencia o relación de implementación. |
| Flecha continua | Comunicación o uso en tiempo de ejecución. |

## Comunicación de los casos de uso

| Caso de uso | Entrada REST | Servicio | Puertos de salida involucrados |
|---|---|---|---|
| Crear cliente | `POST /api/clientes` | `crearCliente` | `ClienteRepositoryPort`, `CorreoRegistradoPort`, `ContrasenaPort` |
| Obtener cliente | `GET /api/clientes/{id}` | `obtenerCliente` | `ClienteRepositoryPort` |
| Listar clientes | `GET /api/clientes` | `obtenerClientes` | `ClienteRepositoryPort` |
| Eliminar cliente | `DELETE /api/clientes/{id}` | `eliminarCliente` | `ClienteRepositoryPort` |

## Lectura del diseño

1. `ClienteController` no contiene reglas de negocio: recibe la petición HTTP y llama al puerto de entrada correspondiente.
2. `ClienteService` implementa las interfaces de los casos de uso. Allí se validan correo, nombre de usuario y contraseña, y se crea o consulta el modelo `Cliente`.
3. El servicio conoce `ClienteRepositoryPort`, `CorreoRegistradoPort` y `ContrasenaPort`, pero no conoce JPA ni BCrypt.
4. `ClienteRepositoryAdapter` implementa el puerto de repositorio y usa la interfaz `ClienteJpaRepository` para persistir `ClienteJpaEntity`. El adaptador convierte entre la entidad JPA y el modelo de dominio `Cliente`.
5. `CorreoRegistradoAdapter` consulta el correo único entre tipos de cuenta y `ContrasenaAdapter` implementa el hash y la comparación BCrypt.

Esta separación permite reemplazar el mecanismo de persistencia o de cifrado modificando los adaptadores sin cambiar los casos de uso de Cliente.
