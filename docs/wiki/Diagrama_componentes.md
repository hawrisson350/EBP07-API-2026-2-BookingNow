# Análisis del diagrama de componentes

> [← Volver a la entrega del Sprint 1](Sprint_1.md)

## Propósito

Este diagrama describe los componentes del backend de BookingNow y evidencia el uso de arquitectura hexagonal. El núcleo de la aplicación contiene los casos de uso y las reglas de negocio; los elementos que dependen de tecnologías externas se ubican como adaptadores.

## Diagrama

::: mermaid
flowchart LR
    consumidor[Consumidor de la API REST]
    baseDatos[(PostgreSQL en Supabase)]

    subgraph backend[BookingNow API - Spring Boot]
        direction LR

        subgraph entrada[Adaptadores de entrada]
            seguridad[Spring Security<br/>Validación JWT y CORS]
            controladores[Controladores REST<br/>Auth, Cliente, Proveedor,<br/>Negocio y Servicio]
        end

        subgraph aplicacion[Aplicación - Núcleo hexagonal]
            puertosEntrada[Puertos de entrada<br/>Casos de uso]
            servicios[Servicios de aplicación<br/>Reglas de negocio]
            puertosSalida[Puertos de salida<br/>Contratos de persistencia y seguridad]
        end

        subgraph salida[Adaptadores de salida]
            repositorios[Repositorios JPA<br/>Hibernate]
            contrasena[Adaptador BCrypt]
            tokens[Adaptador JWT]
        end
    end

    consumidor -->|HTTP · JSON · /api| seguridad
    seguridad --> controladores
    controladores --> puertosEntrada
    puertosEntrada --> servicios
    servicios --> puertosSalida
    servicios --> contrasena
    servicios --> tokens
    puertosSalida --> repositorios
    repositorios -->|JDBC · SSL| baseDatos

    classDef external fill:#E8F1FF,stroke:#2563EB,color:#172554
    classDef adapter fill:#ECFDF5,stroke:#059669,color:#064E3B
    classDef core fill:#FFF7ED,stroke:#EA580C,color:#7C2D12
    classDef database fill:#FDF2F8,stroke:#DB2777,color:#831843
    class consumidor external
    class seguridad,controladores,repositorios,contrasena,tokens adapter
    class puertosEntrada,servicios,puertosSalida core
    class baseDatos database
:::

## Lectura del diagrama

| Elemento | Responsabilidad |
|---|---|
| Adaptadores de entrada | Reciben solicitudes HTTP, validan el JWT y traducen la petición REST hacia la aplicación. |
| Puertos de entrada | Definen los casos de uso que puede ejecutar el sistema, como registrar un cliente, proveedor, negocio o servicio. |
| Servicios de aplicación | Ejecutan las reglas de negocio y coordinan los casos de uso. No dependen de HTTP ni de JPA. |
| Puertos de salida | Definen los contratos que requiere el núcleo para persistir información o aplicar seguridad. |
| Adaptadores de salida | Implementan esos contratos con JPA/Hibernate, BCrypt y JWT. |
| PostgreSQL en Supabase | Almacena la información del sistema y se conecta mediante JDBC con SSL. |

## Por qué representa arquitectura hexagonal

La dependencia apunta desde los adaptadores hacia los puertos del núcleo. Los controladores REST no contienen las reglas del negocio y los servicios de aplicación no conocen detalles de PostgreSQL, Spring MVC o la interfaz del cliente. Por ello, un cambio de base de datos o de interfaz puede resolverse en los adaptadores sin reescribir los casos de uso.
