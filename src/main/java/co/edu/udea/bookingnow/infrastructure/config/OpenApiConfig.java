package co.edu.udea.bookingnow.infrastructure.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.List;

@Configuration
public class OpenApiConfig {
    @Bean
    OpenAPI bookingNowOpenApi() {
        return new OpenAPI()
            .info(new Info().title("BookingNow API").version("0.0.1")
                .description("Registro e inicio de sesion de clientes y proveedores. "
                    + "Primero ejecuta GET /api/auth/csrf y copia token en Authorize > csrfToken. "
                    + "Despues registra una cuenta e inicia sesion con su endpoint /login. "
                    + "El navegador conserva la cookie de sesion automaticamente. "
                    + "Tras logout o expiracion, solicita un nuevo token CSRF. No se utiliza JWT."))
            .components(new Components().addSecuritySchemes("csrfToken", new SecurityScheme()
                .type(SecurityScheme.Type.APIKEY).in(SecurityScheme.In.HEADER).name("X-CSRF-TOKEN")
                .description("Valor token de GET /api/auth/csrf, obtenido en este mismo navegador. No es la contrasena.")));
    }

    @Bean
    OpenApiCustomizer documentarOperaciones() {
        return api -> {
            // Logout lo atiende un filtro de Spring Security, no un controlador.
            api.path("/api/auth/logout", new PathItem().post(new Operation()
                .operationId("cerrarSesion").summary("Cerrar sesion")
                .responses(new ApiResponses().addApiResponse("204", new ApiResponse().description("Sesion cerrada")))));
            api.getPaths().forEach((path, item) -> {
                // El listado de cuentas esta bloqueado hasta definir administradores.
                if (path.equals("/api/clientes") || path.equals("/api/proveedores")) {
                    item.setGet(null);
                }
                item.readOperationsMap().forEach((method, operation) -> {
                    String tag = path.contains("clientes") ? "Clientes"
                            : path.contains("proveedores") ? "Proveedores" : "Autenticacion";
                    operation.setTags(List.of(tag));
                    if (operation.getSummary() == null) {
                        operation.setSummary(path.endsWith("/login") ? "Iniciar sesion"
                            : path.endsWith("/csrf") ? "Obtener token CSRF"
                            : method == PathItem.HttpMethod.POST ? "Registrar cuenta"
                            : method == PathItem.HttpMethod.DELETE ? "Eliminar mi cuenta" : "Consultar mi cuenta");
                    }
                    if (method == PathItem.HttpMethod.POST || method == PathItem.HttpMethod.DELETE) {
                        operation.addSecurityItem(new SecurityRequirement().addList("csrfToken"));
                        operation.getResponses().addApiResponse("403", new ApiResponse().description("Token CSRF ausente/invalido o acceso no permitido"));
                    }
                    if (path.endsWith("/login")) {
                        operation.getResponses().addApiResponse("401", new ApiResponse().description("Credenciales incorrectas"));
                    }
                    if (path.endsWith("/{id}")) {
                        operation.setDescription("Requiere iniciar sesion como titular de esta cuenta. La cookie se envia automaticamente desde Swagger UI.");
                        operation.getResponses().addApiResponse("401", new ApiResponse().description("Sesion requerida"));
                        operation.getResponses().addApiResponse("403", new ApiResponse().description("La cuenta pertenece a otra identidad"));
                    }
                    if (method == PathItem.HttpMethod.POST && !path.endsWith("/logout")) {
                        operation.getResponses().addApiResponse("400", new ApiResponse().description("Datos invalidos"));
                        if (!path.endsWith("/login")) {
                            operation.getResponses().addApiResponse("409", new ApiResponse().description("Nombre de usuario duplicado"));
                        }
                    }
                });
            });
            if (api.getComponents().getSchemas() != null) {
                api.getComponents().getSchemas().values().forEach(schema -> {
                    if (schema.getProperties() != null && schema.getProperties().containsKey("contrasena")) {
                        var password = (io.swagger.v3.oas.models.media.Schema<?>) schema.getProperties().get("contrasena");
                        password.setFormat("password");
                        password.setWriteOnly(true);
                    }
                });
            }
        };
    }
}
