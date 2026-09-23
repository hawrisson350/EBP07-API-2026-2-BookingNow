# Diagrama de componentes: Cliente

> [← Volver al índice de componentes](Diagrama_componentes.md)

## Alcance

El diagrama describe únicamente el diseño interno de la entidad **Cliente**. Muestra cómo una solicitud REST llega al caso de uso y cómo el servicio se comunica con la persistencia y la seguridad mediante puertos. No representa frontend, despliegue, redes ni base de datos física.

## Diagrama

::: mermaid
flowchart LR
    subgraph entrada[Adaptador de entrada REST]
        controller[ClienteController<br/>/api/clientes]
        dto[RegistrarClienteCommand<br/>ClienteResponse]
    end

    subgraph aplicacion[Aplicación]
        subgraph puertosIn[Puertos de entrada - Interfaces]
            crear[CrearClienteUseCase<br/>crearCliente]
            obtener[ObtenerClienteUseCase<br/>obtenerCliente]
            listar[ObtenerClientesUseCase<br/>obtenerClientes]
            eliminar[EliminarClienteUseCase<br/>eliminarCliente]
        end

        servicio[ClienteService<br/>Implementa los casos de uso]

        subgraph puertosOut[Puertos de salida - Interfaces]
            clientePort[ClienteRepositoryPort]
            correoPort[CorreoRegistradoPort]
            contrasenaPort[ContrasenaPort]
        end
    end

    subgraph dominio[Dominio]
        cliente[Modelo Cliente]
        validaciones[ValidacionCuenta<br/>ValidacionLogin]
    end

    subgraph salida[Adaptadores de salida]
        clienteAdapter[ClienteRepositoryAdapter]
        jpaRepository[ClienteJpaRepository<br/>Interfaz JPA]
        jpaEntity[ClienteJpaEntity]
        correoAdapter[CorreoRegistradoAdapter]
        correoEntity[CorreoRegistradoJpaEntity]
        contrasenaAdapter[ContrasenaAdapter<br/>BCrypt]
    end

    dto --> controller
    controller --> crear
    controller --> obtener
    controller --> listar
    controller --> eliminar

    crear --> servicio
    obtener --> servicio
    listar --> servicio
    eliminar --> servicio
    servicio --> cliente
    servicio --> validaciones
    servicio --> clientePort
    servicio --> correoPort
    servicio --> contrasenaPort

    clientePort -. implementado por .-> clienteAdapter
    correoPort -. implementado por .-> correoAdapter
    contrasenaPort -. implementado por .-> contrasenaAdapter
    clienteAdapter --> jpaRepository
    jpaRepository --> jpaEntity
    clienteAdapter <-->|mapea| cliente
    correoAdapter --> correoEntity

    classDef entry fill:#E8F1FF,stroke:#2563EB,color:#172554
    classDef port fill:#FFF7ED,stroke:#EA580C,color:#7C2D12
    classDef service fill:#FEF3C7,stroke:#D97706,color:#78350F
    classDef domain fill:#F3E8FF,stroke:#9333EA,color:#581C87
    classDef adapter fill:#ECFDF5,stroke:#059669,color:#064E3B
    class controller,dto entry
    class crear,obtener,listar,eliminar,clientePort,correoPort,contrasenaPort port
    class servicio service
    class cliente,validaciones domain
    class clienteAdapter,jpaRepository,jpaEntity,correoAdapter,correoEntity,contrasenaAdapter adapter
:::

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
