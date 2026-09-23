# Diagrama de componentes: Cliente

> [← Volver al índice de componentes](Diagrama_componentes.md)

## Alcance

El diagrama describe únicamente el diseño interno de la entidad **Cliente**. Muestra cómo una solicitud REST llega al caso de uso y cómo el servicio se comunica con la persistencia y la seguridad mediante puertos. No representa frontend, despliegue, redes ni base de datos física.

## Diagrama

El diagrama usa el nivel **C4 Component**. El rol de cada pieza de la arquitectura hexagonal se indica en el campo de tecnología: adaptador de entrada, puerto de entrada, servicio de aplicación, modelo de dominio, puerto de salida o adaptador de salida.

::: mermaid
C4Component
title Diagrama C4 de componentes - Cliente

Person_Ext(consumidor, "Consumidor REST", "Invoca las operaciones de Cliente")

Container_Boundary(api, "BookingNow API - diseño interno de Cliente") {
    Component(dto, "RegistrarClienteCommand y ClienteResponse", "DTO REST", "Transportan datos entre HTTP y la aplicación")
    Component(controller, "ClienteController", "Adaptador de entrada · Spring MVC", "Expone /api/clientes")

    Component(crear, "CrearClienteUseCase", "Puerto de entrada · Interface", "Declara crearCliente")
    Component(obtener, "ObtenerClienteUseCase", "Puerto de entrada · Interface", "Declara obtenerCliente")
    Component(listar, "ObtenerClientesUseCase", "Puerto de entrada · Interface", "Declara obtenerClientes")
    Component(eliminar, "EliminarClienteUseCase", "Puerto de entrada · Interface", "Declara eliminarCliente")

    Component(servicio, "ClienteService", "Servicio de aplicación", "Implementa casos de uso y aplica reglas")
    Component(cliente, "Cliente", "Modelo de dominio", "Representa la cuenta cliente")
    Component(validaciones, "ValidacionCuenta y ValidacionLogin", "Reglas de dominio", "Validan datos de cuenta y credenciales")

    Component(clientePort, "ClienteRepositoryPort", "Puerto de salida · Interface", "Contrato de persistencia de Cliente")
    Component(correoPort, "CorreoRegistradoPort", "Puerto de salida · Interface", "Verifica correo único entre roles")
    Component(contrasenaPort, "ContrasenaPort", "Puerto de salida · Interface", "Contrato para hash y comparación")

    Component(clienteAdapter, "ClienteRepositoryAdapter", "Adaptador de salida · Repository", "Implementa ClienteRepositoryPort")
    Component(jpaRepository, "ClienteJpaRepository", "Interface JPA", "Operaciones JPA de ClienteJpaEntity")
    Component(jpaEntity, "ClienteJpaEntity", "Entidad de persistencia", "Representación JPA de Cliente")
    Component(correoAdapter, "CorreoRegistradoAdapter", "Adaptador de salida", "Implementa CorreoRegistradoPort")
    Component(correoEntity, "CorreoRegistradoJpaEntity", "Entidad de persistencia", "Registro de correo y tipo de cuenta")
    Component(contrasenaAdapter, "ContrasenaAdapter", "Adaptador de salida · BCrypt", "Codifica y compara contraseñas")
}

Rel(consumidor, controller, "Envía solicitud", "HTTP/JSON")
Rel(dto, controller, "Entrega y recibe datos")
Rel(controller, crear, "Depende de")
Rel(controller, obtener, "Depende de")
Rel(controller, listar, "Depende de")
Rel(controller, eliminar, "Depende de")
Rel(servicio, crear, "Implementa")
Rel(servicio, obtener, "Implementa")
Rel(servicio, listar, "Implementa")
Rel(servicio, eliminar, "Implementa")
Rel(servicio, cliente, "Crea, consulta y elimina")
Rel(servicio, validaciones, "Aplica")
Rel(servicio, clientePort, "Depende de")
Rel(servicio, correoPort, "Depende de")
Rel(servicio, contrasenaPort, "Depende de")
Rel(clienteAdapter, clientePort, "Implementa")
Rel(correoAdapter, correoPort, "Implementa")
Rel(contrasenaAdapter, contrasenaPort, "Implementa")
Rel(clienteAdapter, jpaRepository, "Usa")
Rel(jpaRepository, jpaEntity, "Gestiona")
Rel(clienteAdapter, cliente, "Mapea")
Rel(correoAdapter, correoEntity, "Consulta")

UpdateLayoutConfig($c4ShapeInRow="3", $c4BoundaryInRow="1")
:::

## Lectura C4 y arquitectura hexagonal

| Elemento C4 | Rol en BookingNow |
|---|---|
| `Container_Boundary` | Delimita la API y todos los componentes internos que participan en Cliente. |
| `Component` con tecnología **Adaptador de entrada** | `ClienteController` y DTOs que traducen HTTP al lenguaje de la aplicación. |
| `Component` con tecnología **Puerto de entrada** | Interfaces de los casos de uso consumidas por el controlador. |
| `Component` con tecnología **Servicio de aplicación** | `ClienteService`, que implementa los puertos de entrada y coordina el dominio. |
| `Component` con tecnología **Puerto de salida** | Interfaces que protegen al núcleo de JPA y BCrypt. |
| `Component` con tecnología **Adaptador de salida** | Implementaciones tecnológicas de los puertos de salida. |

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
