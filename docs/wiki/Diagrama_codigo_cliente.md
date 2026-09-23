# Diagrama de código: Cliente

> [← Volver al índice de componentes](Diagrama_componentes.md)

## Nivel C4

Este es el **nivel 4 de C4: Código**. Representa las clases, interfaces y relaciones reales que intervienen en la entidad Cliente. Se usa `classDiagram` de Mermaid, pues Mermaid no ofrece un tipo `C4Code`.

## Diagrama de clases

::: mermaid
classDiagram
    direction LR

    class ClienteController {
        <<RestController>>
        -CrearClienteUseCase crear
        -ObtenerClienteUseCase obtener
        -ObtenerClientesUseCase listar
        -EliminarClienteUseCase eliminar
        +crear(RegistrarClienteCommand) RegistroResponse
        +listar() List~ClienteResponse~
        +obtener(Long) ResponseEntity
        +eliminar(Long) void
    }

    class AuthController {
        <<RestController>>
        -IniciarSesionUseCase autenticar
        +login(CredencialesCommand) LoginResponse
    }

    class AutenticacionService {
        <<Application Service>>
        -CorreoRegistradoPort correos
        -IniciarSesionClienteUseCase clientes
        +iniciarSesion(CredencialesCommand) CuentaAutenticada
    }

    class CrearClienteUseCase {
        <<interface>>
        +crearCliente(RegistrarClienteCommand) Cliente
    }
    class ObtenerClienteUseCase {
        <<interface>>
        +obtenerCliente(Long) Optional~Cliente~
    }
    class ObtenerClientesUseCase {
        <<interface>>
        +obtenerClientes() List~Cliente~
    }
    class EliminarClienteUseCase {
        <<interface>>
        +eliminarCliente(Long) void
    }
    class IniciarSesionClienteUseCase {
        <<interface>>
        +iniciarSesionCliente(CredencialesCommand) Cliente
    }

    class ClienteService {
        <<Application Service>>
        -ClienteRepositoryPort repository
        -CorreoRegistradoPort correos
        -ContrasenaPort contrasenaPort
        +crearCliente(RegistrarClienteCommand) Cliente
        +obtenerCliente(Long) Optional~Cliente~
        +obtenerClientes() List~Cliente~
        +eliminarCliente(Long) void
        +iniciarSesionCliente(CredencialesCommand) Cliente
    }

    class Cliente {
        <<Domain Model>>
        -Long idCliente
        -String correo
        -String nombreUsuario
        -String contrasenaHash
        -EstadoCuenta estado
    }
    class ValidacionCuenta {
        <<Domain Validation>>
        +correo(String) String
        +nombreUsuario(String) String
        +contrasena(String) void
    }
    class ValidacionLogin {
        <<Domain Validation>>
        +validar(CredencialesCommand) void
    }

    class ClienteRepositoryPort {
        <<interface · Port Out>>
        +guardar(Cliente) Cliente
        +obtenerTodos() List~Cliente~
        +obtenerPorId(Long) Optional~Cliente~
        +obtenerPorCorreo(String) Optional~Cliente~
        +existeNombreUsuario(String) boolean
        +eliminar(Long) void
    }
    class CorreoRegistradoPort {
        <<interface · Port Out>>
        +tipoPorCorreo(String) Optional~String~
    }
    class ContrasenaPort {
        <<interface · Port Out>>
        +codificar(String) String
        +coincide(String, String) boolean
    }

    class ClienteRepositoryAdapter {
        <<Repository · Adapter Out>>
        -ClienteJpaRepository repository
        +guardar(Cliente) Cliente
        +obtenerTodos() List~Cliente~
        +obtenerPorId(Long) Optional~Cliente~
        +obtenerPorCorreo(String) Optional~Cliente~
        +eliminar(Long) void
    }
    class ClienteJpaRepository {
        <<interface · Spring Data JPA>>
        +findByCorreo(String) Optional~ClienteJpaEntity~
        +existsByNombreUsuario(String) boolean
    }
    class JpaRepository {
        <<Spring Data Interface>>
    }
    class ClienteJpaEntity {
        <<JPA Entity>>
        -Long idCliente
        -String correo
        -String nombreUsuario
        -String contrasenaHash
        -EstadoCuenta estado
    }
    class CorreoRegistradoAdapter {
        <<Adapter Out>>
        +tipoPorCorreo(String) Optional~String~
    }
    class CorreoRegistradoJpaEntity {
        <<JPA Entity>>
        -String correo
        -String tipo
    }
    class ContrasenaAdapter {
        <<Adapter Out · BCrypt>>
        +codificar(String) String
        +coincide(String, String) boolean
    }

    ClienteController --> CrearClienteUseCase : depende
    ClienteController --> ObtenerClienteUseCase : depende
    ClienteController --> ObtenerClientesUseCase : depende
    ClienteController --> EliminarClienteUseCase : depende
    AuthController --> AutenticacionService : usa
    AutenticacionService --> IniciarSesionClienteUseCase : depende

    ClienteService ..|> CrearClienteUseCase : implementa
    ClienteService ..|> ObtenerClienteUseCase : implementa
    ClienteService ..|> ObtenerClientesUseCase : implementa
    ClienteService ..|> EliminarClienteUseCase : implementa
    ClienteService ..|> IniciarSesionClienteUseCase : implementa
    ClienteService --> Cliente : crea y consulta
    ClienteService --> ValidacionCuenta : valida registro
    ClienteService --> ValidacionLogin : valida login
    ClienteService --> ClienteRepositoryPort : depende
    ClienteService --> CorreoRegistradoPort : depende
    ClienteService --> ContrasenaPort : depende

    ClienteRepositoryAdapter ..|> ClienteRepositoryPort : implementa
    CorreoRegistradoAdapter ..|> CorreoRegistradoPort : implementa
    ContrasenaAdapter ..|> ContrasenaPort : implementa
    ClienteRepositoryAdapter --> ClienteJpaRepository : usa
    ClienteJpaRepository --|> JpaRepository : extiende
    ClienteJpaRepository --> ClienteJpaEntity : gestiona
    ClienteRepositoryAdapter --> Cliente : mapea
    CorreoRegistradoAdapter --> CorreoRegistradoJpaEntity : consulta
