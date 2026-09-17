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
                .description("Registra una cuenta e inicia sesion con correo y contraseña en /api/auth/login. "
                    + "Copia accessToken en Authorize > bearerAuth. No se necesitan cookies ni token CSRF. "
                    + "El JWT vence a los 30 minutos por defecto."))
            .components(new Components().addSecuritySchemes("bearerAuth", new SecurityScheme()
                .type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")
                .description("Pega solo accessToken, sin el prefijo Bearer.")));

    }

    @Bean
    OpenApiCustomizer documentarOperaciones() {
        return api -> {
            api.getPaths().forEach((path, item) -> {
                item.readOperationsMap().forEach((method, operation) -> {
                    String tag = path.contains("/servicios") ? "Servicios" : path.contains("negocios") ? "Negocios" : path.contains("clientes") ? "Clientes"
                            : path.contains("proveedores") ? "Proveedores" : "Autenticacion";
                    operation.setTags(List.of(tag));
                    if (operation.getSummary() == null) {
                        operation.setSummary((method == PathItem.HttpMethod.GET && (path.equals("/api/clientes") || path.equals("/api/proveedores"))) ? "Listar cuentas (administrador)"
                            : method == PathItem.HttpMethod.GET && path.equals("/api/negocios") ? "Listar negocios (administrador)"
                            : path.endsWith("/login") ? "Iniciar sesion"
                            : method == PathItem.HttpMethod.POST ? (path.contains("servicios") ? "Registrar servicio" : path.contains("negocios") ? "Registrar negocio" : "Registrar cuenta")
                            : method == PathItem.HttpMethod.DELETE ? "Eliminar mi cuenta" : path.contains("servicios") ? "Listar servicios del negocio" : path.contains("negocios") ? "Consultar mi negocio y disponibilidad de registro" : "Consultar mi cuenta");
                    }
                    if (path.endsWith("/login")) {
                        operation.getResponses().addApiResponse("401", new ApiResponse().description("Credenciales incorrectas"));
                    }
                    if (method == PathItem.HttpMethod.GET && (path.equals("/api/clientes") || path.equals("/api/proveedores") || path.equals("/api/negocios"))) {
                        operation.addSecurityItem(new SecurityRequirement().addList("bearerAuth"));
                        operation.setDescription("Requiere JWT con rol ADMINISTRADOR.");
                        operation.getResponses().addApiResponse("401", new ApiResponse().description("JWT ausente, inválido o vencido"));
                        operation.getResponses().addApiResponse("403", new ApiResponse().description("La cuenta no tiene rol administrador"));
                    }
                    if (path.endsWith("/{id}") || (path.startsWith("/api/negocios") && !path.equals("/api/negocios"))) {
                        operation.addSecurityItem(new SecurityRequirement().addList("bearerAuth"));
                        boolean consultaServicios = method == PathItem.HttpMethod.GET && path.contains("/servicios");
                        operation.setDescription(consultaServicios
                                ? "Requiere JWT del proveedor propietario o de un ADMINISTRADOR."
                                : "Requiere JWT del proveedor propietario.");
                        operation.getResponses().addApiResponse("401", new ApiResponse().description("JWT ausente, invalido o vencido"));
                        operation.getResponses().addApiResponse("403", new ApiResponse().description("La cuenta pertenece a otra identidad"));
                    }
                    if (method == PathItem.HttpMethod.POST) {
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
