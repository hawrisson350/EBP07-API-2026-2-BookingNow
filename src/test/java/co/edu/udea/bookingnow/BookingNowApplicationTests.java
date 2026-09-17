package co.edu.udea.bookingnow;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import tools.jackson.databind.json.JsonMapper;
import java.net.*;
import java.net.http.*;
import java.util.UUID;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class BookingNowApplicationTests {
    @LocalServerPort private int port;
    @Autowired private JdbcTemplate jdbc;
    private final JsonMapper mapper = JsonMapper.builder().build();

    private HttpClient client() {
        return HttpClient.newBuilder().cookieHandler(new CookieManager(null, CookiePolicy.ACCEPT_ALL)).build();
    }

    private HttpResponse<String> request(HttpClient client, String method, String path, String body) throws Exception {
        var builder = HttpRequest.newBuilder(URI.create("http://localhost:" + port + path))
                .header("Content-Type", "application/json");
        if (!method.equals("GET")) {
            var csrf = mapper.readTree(request(client, "GET", "/api/auth/csrf", null).body());
            builder.header(csrf.get("headerName").asText(), csrf.get("token").asText());
        }
        return client.send(builder.method(method, body == null ? HttpRequest.BodyPublishers.noBody()
                : HttpRequest.BodyPublishers.ofString(body)).build(), HttpResponse.BodyHandlers.ofString());
    }

    private String registro(String nombre, boolean proveedor) {
        return "{\"correo\":\"demo@example.com\",\"nombreUsuario\":\"" + nombre
                + "\",\"contrasena\":\"ClaveDemo123!\""
                + (proveedor ? ",\"razonSocial\":\"Negocio Demo\",\"nit\":\"900123456-7\"" : "") + "}";
    }

    private String credenciales(String nombre, String clave) {
        return "{\"nombreUsuario\":\"" + nombre + "\",\"contrasena\":\"" + clave + "\"}";
    }

    @ParameterizedTest
    @CsvSource({"clientes,idCliente,false", "proveedores,idProveedor,true"})
    void registroLoginYAccesoPropio(String ruta, String idCampo, boolean proveedor) throws Exception {
        String nombre = "demo_" + UUID.randomUUID();
        try (var client = client(); var otro = client()) {
            var created = request(client, "POST", "/api/" + ruta, registro(nombre, proveedor));
            assertThat(created.statusCode()).isEqualTo(201);
            assertThat(created.body()).doesNotContain("contrasena", "ClaveDemo", "$2");
            var cuenta = mapper.readTree(created.body());
            long id = cuenta.get(idCampo).asLong();
            assertThat(id).isPositive();
            if (proveedor) {
                assertThat(cuenta.get("razonSocial").asText()).isEqualTo("Negocio Demo");
                assertThat(cuenta.get("nit").asText()).isEqualTo("900123456-7");
            }
            String hash = jdbc.queryForObject("select contrasena_hash from " + ruta + " where nombre_usuario = ?", String.class, nombre);
            assertThat(new BCryptPasswordEncoder().matches("ClaveDemo123!", hash)).isTrue();
            assertThat(request(client, "GET", "/api/" + ruta + "/" + id, null).statusCode()).isEqualTo(401);
            assertThat(request(client, "POST", "/api/" + ruta, registro(nombre.toUpperCase(), proveedor)).statusCode()).isEqualTo(409);
            assertThat(request(client, "POST", "/api/" + ruta + "/login", credenciales(nombre, "incorrecta")).statusCode()).isEqualTo(401);
            assertThat(request(client, "POST", "/api/" + ruta + "/login", credenciales("inexistente", "incorrecta")).statusCode()).isEqualTo(401);
            var login = request(client, "POST", "/api/" + ruta + "/login", credenciales(nombre, "ClaveDemo123!"));
            assertThat(login.statusCode()).isEqualTo(200);
            assertThat(mapper.readTree(login.body())).isEqualTo(cuenta);
            var found = request(client, "GET", "/api/" + ruta + "/" + id, null);
            assertThat(found.statusCode()).isEqualTo(200);
            assertThat(mapper.readTree(found.body())).isEqualTo(cuenta);
            assertThat(request(client, "GET", "/api/" + ruta, null).statusCode()).isEqualTo(403);
            String otroNombre = "otro_" + UUID.randomUUID();
            request(otro, "POST", "/api/" + ruta, registro(otroNombre, proveedor));
            request(otro, "POST", "/api/" + ruta + "/login", credenciales(otroNombre, "ClaveDemo123!"));
            assertThat(request(otro, "GET", "/api/" + ruta + "/" + id, null).statusCode()).isEqualTo(403);
            assertThat(request(otro, "DELETE", "/api/" + ruta + "/" + id, null).statusCode()).isEqualTo(403);
            assertThat(request(client, "POST", "/api/auth/logout", null).statusCode()).isEqualTo(204);
            assertThat(request(client, "GET", "/api/" + ruta + "/" + id, null).statusCode()).isEqualTo(401);
            request(client, "POST", "/api/" + ruta + "/login", credenciales(nombre, "ClaveDemo123!"));
            assertThat(request(client, "DELETE", "/api/" + ruta + "/" + id, null).statusCode()).isEqualTo(204);
            assertThat(request(client, "POST", "/api/" + ruta + "/login", credenciales(nombre, "ClaveDemo123!" )).statusCode()).isEqualTo(401);
        }
    }

    @Test
    void separaIdentidadesClienteYProveedor() throws Exception {
        String nombre = "compartido_" + UUID.randomUUID();
        // Los nombres se acortan para respetar el limite de 50 caracteres.
        nombre = nombre.substring(0, 45);
        try (var client = client()) {
            var cliente = request(client, "POST", "/api/clientes", registro(nombre, false));
            var proveedor = request(client, "POST", "/api/proveedores", registro(nombre, true));
            assertThat(cliente.statusCode()).isEqualTo(201);
            assertThat(proveedor.statusCode()).isEqualTo(201);
            long idCliente = mapper.readTree(cliente.body()).get("idCliente").asLong();
            long idProveedor = mapper.readTree(proveedor.body()).get("idProveedor").asLong();
            assertThat(request(client, "POST", "/api/clientes/login", credenciales(nombre, "ClaveDemo123!")).statusCode()).isEqualTo(200);
            assertThat(request(client, "GET", "/api/proveedores/" + idProveedor, null).statusCode()).isEqualTo(403);
            assertThat(request(client, "POST", "/api/proveedores/login", credenciales(nombre, "ClaveDemo123!")).statusCode()).isEqualTo(200);
            assertThat(request(client, "GET", "/api/clientes/" + idCliente, null).statusCode()).isEqualTo(403);
            assertThat(request(client, "GET", "/api/proveedores/" + idProveedor, null).statusCode()).isEqualTo(200);
        }
    }

    @Test
    void validaCamposYRequiereCsrf() throws Exception {
        try (var client = client()) {
            for (String ruta : new String[]{"clientes", "proveedores"}) {
                for (String body : new String[]{"{}", registro("ab", ruta.equals("proveedores")),
                        registro("valido", ruta.equals("proveedores")).replace("demo@example.com", "invalido"),
                        registro("valido", ruta.equals("proveedores")).replace("ClaveDemo123!", "corta")}) {
                    assertThat(request(client, "POST", "/api/" + ruta, body).statusCode()).isEqualTo(400);
                }
            }
            var sinCsrf = HttpRequest.newBuilder(URI.create("http://localhost:" + port + "/api/clientes"))
                    .header("Content-Type", "application/json").POST(HttpRequest.BodyPublishers.ofString(registro("nuevo", false))).build();
            assertThat(client.send(sinCsrf, HttpResponse.BodyHandlers.ofString()).statusCode()).isEqualTo(403);
        }
    }

    @Test
    void healthReturnsUp() throws Exception {
        try (var client = client()) {
            var response = request(client, "GET", "/health", null);
            assertThat(response.statusCode()).isEqualTo(200);
            assertThat(response.body()).isEqualTo("{\"status\":\"UP\"}");
        }
    }

    @Test
    void swaggerEsPublicoYDocumentaCsrfSinExponerHashes() throws Exception {
        try (var client = client()) {
            var ui = request(client, "GET", "/swagger-ui/index.html", null);
            assertThat(ui.statusCode()).isEqualTo(200);
            assertThat(ui.body()).contains("Swagger UI");
            var response = request(client, "GET", "/v3/api-docs", null);
            assertThat(response.statusCode()).isEqualTo(200);
            assertThat(response.body()).doesNotContain("contrasenaHash");
            var api = mapper.readTree(response.body());
            assertThat(api.at("/info/title").asText()).isEqualTo("BookingNow API");
            assertThat(api.at("/components/securitySchemes/csrfToken/name").asText()).isEqualTo("X-CSRF-TOKEN");
            assertThat(api.get("paths").has("/api/clientes/login")).isTrue();
            assertThat(api.get("paths").has("/api/proveedores/login")).isTrue();
            assertThat(api.get("paths").get("/api/clientes").has("get")).isFalse();
            assertThat(api.get("paths").get("/api/auth/logout").get("post").has("security")).isTrue();
            var config = request(client, "GET", "/v3/api-docs/swagger-config", null);
            assertThat(config.statusCode()).isEqualTo(200);
        }
    }
}
