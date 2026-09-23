# Modelado de componentes por entidad

> [← Volver a la entrega del Sprint 1](Sprint_1.md)

Esta sección documenta la comunicación interna de cada entidad del backend. Cada diagrama muestra el recorrido desde el adaptador REST hasta los puertos y adaptadores de salida, sin incluir despliegue, redes ni infraestructura de nube.

| Entidad | Estado | Análisis |
|---|---|---|
| Cliente | Nivel 4 - Código | [Diagrama de código de Cliente](Diagrama_codigo_cliente.md) |
| Proveedor | Pendiente | Se agregará al continuar el modelado. |
| Negocio | Pendiente | Se agregará al continuar el modelado. |
| Servicio | Pendiente | Se agregará al continuar el modelado. |

## Convención

- **Adaptador de entrada:** traduce HTTP hacia casos de uso.
- **Puerto de entrada:** interfaz que declara un caso de uso.
- **Servicio de aplicación:** implementa los casos de uso y coordina las reglas del dominio.
- **Puerto de salida:** interfaz que declara una dependencia requerida por el servicio.
- **Adaptador de salida:** implementación tecnológica del puerto, como JPA o BCrypt.
