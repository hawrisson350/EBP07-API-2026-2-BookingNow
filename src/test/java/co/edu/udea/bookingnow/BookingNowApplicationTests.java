package co.edu.udea.bookingnow;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.test.context.ActiveProfiles;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.databind.JsonNode;
import java.net.URI;
import java.net.http.*;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class BookingNowApplicationTests {
    @LocalServerPort private int port;
    @Autowired private JdbcTemplate jdbc;
    @Autowired private JwtEncoder encoder;
    private final JsonMapper mapper = JsonMapper.builder().build();
    private static final AtomicInteger SEQUENCE = new AtomicInteger(100000000);
    private final HttpClient http = HttpClient.newHttpClient();
    @AfterEach void cerrarClienteHttp() { http.close(); }
    record Cuenta(long id, String correo, String token, String ruta) {}

    private HttpResponse<String> req(String method, String path, Object body, String token) throws Exception {
        var builder = HttpRequest.newBuilder(URI.create("http://localhost:" + port + path)).header("Content-Type", "application/json");
        if (token != null) builder.header("Authorization", "Bearer " + token);
        return http.send(builder.method(method, body == null ? HttpRequest.BodyPublishers.noBody()
                : HttpRequest.BodyPublishers.ofString(mapper.writeValueAsString(body))).build(), HttpResponse.BodyHandlers.ofString());
    }
    private JsonNode json(HttpResponse<String> r) { return mapper.readTree(r.body()); }
    private Map<String,Object> registro(boolean proveedor) {
        String n = "demo_" + UUID.randomUUID();
        var body = new LinkedHashMap<String,Object>();
        body.put("correo", n + "@example.com"); body.put("nombreUsuario", n); body.put("contrasena", "ClaveDemo123!");
        if (proveedor) { body.put("razonSocial", "Empresa " + n); body.put("nit", SEQUENCE.incrementAndGet() + "-1"); }
        return body;
    }
    private Cuenta cuenta(boolean proveedor) throws Exception {
        var body = registro(proveedor); String ruta = proveedor ? "proveedores" : "clientes";
        var r = req("POST", "/api/"+ruta, body, null); assertThat(r.statusCode()).isEqualTo(201);
        var login = req("POST", "/api/auth/login", Map.of("correo",body.get("correo"),"contrasena","ClaveDemo123!"), null);
        assertThat(login.statusCode()).isEqualTo(200);
        return new Cuenta(json(r).get("datos").get(proveedor ? "idProveedor" : "idCliente").asLong(),
                (String)body.get("correo"), json(login).get("accessToken").asText(), ruta);
    }
    private Map<String,Object> negocio(boolean virtual) {
        var body = new LinkedHashMap<String,Object>();
        body.put("nombre", "Consultorio Demo"); body.put("correo", "negocio@example.com"); body.put("numContacto", "+57 3001234567");
        body.put("categoria", "Consultoria"); body.put("modalidadVirtual", virtual);
        if (!virtual) body.put("direccion", "Calle 10 # 20-30");
        body.put("fotoPrincipal", "https://example.com/portada.jpg");
        body.put("galeria", List.of(Map.of("url","https://example.com/foto.jpg","tipo","IMAGEN"), Map.of("url","https://example.com/video.mp4","tipo","VIDEO")));
        return body;
    }
    private long crearNegocio(Cuenta c) throws Exception {
        var r = req("POST", "/api/negocios", negocio(true), c.token());
        assertThat(r.statusCode()).isEqualTo(201); return json(r).get("datos").get("idNegocio").asLong();
    }
    private Map<String,Object> servicio() {
        return new LinkedHashMap<>(Map.of("nombre","Asesoria","duracionMinutos",30,"precio",new java.math.BigDecimal("20.50"),
                "descripcion","Asesoria inicial","imagenReferencia","https://example.com/servicio.jpg"));
    }

    @ParameterizedTest @ValueSource(booleans={false,true})
    void registroLoginCorreoYTitularidad(boolean proveedor) throws Exception {
        var datos = registro(proveedor); String ruta=proveedor?"proveedores":"clientes";
        var created=req("POST","/api/"+ruta,datos,null);
        assertThat(created.statusCode()).isEqualTo(201);
        assertThat(json(created).get("mensaje").asText()).contains("creada");
        assertThat(created.body()).doesNotContain("contrasena","ClaveDemo123","$2");
        long id=json(created).get("datos").get(proveedor?"idProveedor":"idCliente").asLong();
        String hash=jdbc.queryForObject("select contrasena_hash from "+ruta+" where correo=?",String.class,datos.get("correo"));
        assertThat(new BCryptPasswordEncoder().matches("ClaveDemo123!",hash)).isTrue();
        long inicio=System.nanoTime();
        var login=req("POST","/api/auth/login",Map.of("correo",datos.get("correo").toString().toUpperCase(),"contrasena","ClaveDemo123!"),null);
        long elapsed=TimeUnit.NANOSECONDS.toMillis(System.nanoTime()-inicio);
        System.out.println("LOGIN_WARM_"+ruta+"_MS="+elapsed);
        assertThat(elapsed).isLessThan(3000);
        assertThat(login.statusCode()).isEqualTo(200);
        assertThat(json(login).get("cuenta").get("rol").asText()).isEqualTo(proveedor?"PROVEEDOR":"CLIENTE");
        assertThat(login.headers().allValues("set-cookie")).isEmpty();
        String token=json(login).get("accessToken").asText();
        assertThat(req("GET","/api/"+ruta+"/"+id,null,null).statusCode()).isEqualTo(401);
        assertThat(req("GET","/api/"+ruta+"/"+id,null,token).statusCode()).isEqualTo(200);
        var otro=cuenta(proveedor);
        assertThat(req("GET","/api/"+ruta+"/"+id,null,otro.token()).statusCode()).isEqualTo(403);
        assertThat(req("DELETE","/api/"+ruta+"/"+id,null,otro.token()).statusCode()).isEqualTo(403);
        assertThat(req("GET","/api/"+ruta,null,token).statusCode()).isEqualTo(403);
        assertThat(req("DELETE","/api/"+ruta+"/"+id,null,token).statusCode()).isEqualTo(204);
        assertThat(req("GET","/api/"+ruta+"/"+id,null,token).statusCode()).isEqualTo(401);
    }

    @ParameterizedTest @ValueSource(booleans={false,true})
    void correoUnicoEntreAmbosRoles(boolean proveedor) throws Exception {
        var body=registro(proveedor); String ruta=proveedor?"proveedores":"clientes";
        assertThat(req("POST","/api/"+ruta,body,null).statusCode()).isEqualTo(201);
        var repetido=registro(!proveedor); repetido.put("correo",body.get("correo").toString().toUpperCase());
        var respuesta=req("POST","/api/"+(proveedor?"clientes":"proveedores"),repetido,null);
        assertThat(respuesta.statusCode()).isEqualTo(409); assertThat(respuesta.body()).contains("correo");
    }

    @ParameterizedTest @ValueSource(booleans={false,true})
    void validaRegistroYComplejidad(boolean proveedor) throws Exception {
        String ruta="/api/"+(proveedor?"proveedores":"clientes");
        for (String password:List.of("corta","sinNumeros!","SinEspecial123","12345678!","A1!".repeat(30))) {
            var body=registro(proveedor);body.put("contrasena",password);
            assertThat(req("POST",ruta,body,null).statusCode()).isEqualTo(400);
        }
        for (String campo:List.of("correo","nombreUsuario","contrasena")) {
            var body=registro(proveedor);body.put(campo,"");assertThat(req("POST",ruta,body,null).statusCode()).isEqualTo(400);
        }
        var body=registro(proveedor);body.put("correo","invalido");assertThat(req("POST",ruta,body,null).statusCode()).isEqualTo(400);
        body=registro(proveedor);body.put("nombreUsuario","ab");assertThat(req("POST",ruta,body,null).statusCode()).isEqualTo(400);
    }

    @Test void validaYEvitaNitYRazonDuplicados() throws Exception {
        for (String nit:List.of("123-1","1234567890","123456789-X","123456789-12")) {
            var body=registro(true);body.put("nit",nit);assertThat(req("POST","/api/proveedores",body,null).statusCode()).isEqualTo(400);
        }
        var corto=registro(true);corto.put("razonSocial","ab");assertThat(req("POST","/api/proveedores",corto,null).statusCode()).isEqualTo(400);
        var base=registro(true);assertThat(req("POST","/api/proveedores",base,null).statusCode()).isEqualTo(201);
        var nit=registro(true);nit.put("nit",base.get("nit"));assertThat(req("POST","/api/proveedores",nit,null).statusCode()).isEqualTo(409);
        var razon=registro(true);razon.put("razonSocial","  "+base.get("razonSocial").toString().toUpperCase()+"  ");
        assertThat(req("POST","/api/proveedores",razon,null).statusCode()).isEqualTo(409);
    }

    @Test void loginNoRevelaExistenciaYValidaCampos() throws Exception {
        var c=cuenta(false);
        var incorrecta=req("POST","/api/auth/login",Map.of("correo",c.correo(),"contrasena","incorrecta"),null);
        var desconocido=req("POST","/api/auth/login",Map.of("correo","noexiste@example.com","contrasena","incorrecta"),null);
        assertThat(incorrecta.statusCode()).isEqualTo(401);assertThat(desconocido.statusCode()).isEqualTo(401);
        assertThat(json(incorrecta).get("detail").asText()).isEqualTo("Correo o contraseña incorrectos");
        assertThat(json(desconocido).get("detail")).isEqualTo(json(incorrecta).get("detail"));
        var vacio=req("POST","/api/auth/login",Map.of(),null);
        assertThat(vacio.statusCode()).isEqualTo(400);assertThat(json(vacio).get("campos").has("correo")).isTrue();
        assertThat(json(vacio).get("campos").has("contrasena")).isTrue();
        assertThat(req("POST","/api/auth/login",Map.of("correo","invalido","contrasena","ClaveDemo123!"),null).statusCode()).isEqualTo(400);
    }

    @Test void usuarioAdministradorIniciaSesionYConsultaListadosGlobales() throws Exception {
        String correo = "admin." + UUID.randomUUID() + "@example.com";
        String hash = new BCryptPasswordEncoder().encode("AdminDemo123!");
        jdbc.update("insert into correos_registrados (correo, tipo) values (?, ?)", correo, "ADMINISTRADOR");
        jdbc.update("insert into usuarios (correo, nombre_usuario, contrasena_hash, rol, estado) values (?, ?, ?, ?, ?)",
                correo, "admin_" + UUID.randomUUID().toString().substring(0, 8), hash, "ADMINISTRADOR", "ACTIVA");
        var login = req("POST", "/api/auth/login", Map.of("correo", correo, "contrasena", "AdminDemo123!"), null);
        assertThat(login.statusCode()).isEqualTo(200);
        assertThat(json(login).get("cuenta").get("rol").asText()).isEqualTo("ADMINISTRADOR");
        assertThat(json(login).get("cuenta").get("roles").get(0).asText()).isEqualTo("ADMINISTRADOR");
        String token = json(login).get("accessToken").asText();
        assertThat(req("GET", "/api/clientes", null, token).statusCode()).isEqualTo(200);
        assertThat(req("GET", "/api/proveedores", null, token).statusCode()).isEqualTo(200);
    }

    @Test void administradorConsultaNegociosYServiciosDeCualquierProveedor() throws Exception {
        var proveedor = cuenta(true);
        long idNegocio = crearNegocio(proveedor);
        String rutaServicios = "/api/negocios/" + idNegocio + "/servicios";
        assertThat(req("POST", rutaServicios, servicio(), proveedor.token()).statusCode()).isEqualTo(201);

        String correo = "admin.consulta." + UUID.randomUUID() + "@example.com";
        jdbc.update("insert into correos_registrados (correo, tipo) values (?, ?)", correo, "ADMINISTRADOR");
        jdbc.update("insert into usuarios (correo, nombre_usuario, contrasena_hash, rol, estado) values (?, ?, ?, ?, ?)",
                correo, "admin_" + UUID.randomUUID().toString().substring(0, 8),
                new BCryptPasswordEncoder().encode("AdminDemo123!"), "ADMINISTRADOR", "ACTIVA");
        var login = req("POST", "/api/auth/login", Map.of("correo", correo, "contrasena", "AdminDemo123!"), null);
        assertThat(login.statusCode()).isEqualTo(200);
        String token = json(login).get("accessToken").asText();

        var negocios = req("GET", "/api/negocios", null, token);
        assertThat(negocios.statusCode()).isEqualTo(200);
        assertThat(json(negocios).findValues("idNegocio").stream().anyMatch(id -> id.asLong() == idNegocio)).isTrue();
        var servicios = req("GET", rutaServicios, null, token);
        assertThat(servicios.statusCode()).isEqualTo(200);
        assertThat(json(servicios).size()).isEqualTo(1);
        assertThat(req("POST", "/api/negocios", negocio(true), token).statusCode()).isEqualTo(403);
    }

    @ParameterizedTest @ValueSource(strings={"INACTIVA","BLOQUEADA"})
    void cuentaNoHabilitadaNoIniciaNiReutilizaToken(String estado) throws Exception {
        for(boolean proveedor:List.of(false,true)) {
            var c=cuenta(proveedor);jdbc.update("update "+c.ruta()+" set estado=? where correo=?",estado,c.correo());
            var r=req("POST","/api/auth/login",Map.of("correo",c.correo(),"contrasena","ClaveDemo123!"),null);
            assertThat(r.statusCode()).isEqualTo(403);assertThat(r.body()).contains("no se encuentra habilitada");
            assertThat(req("GET","/api/"+c.ruta()+"/"+c.id(),null,c.token()).statusCode()).isEqualTo(401);
        }
    }

    @Test void negocioVirtualGaleriaYUnicoProveedor() throws Exception {
        var c=cuenta(true);
        assertThat(json(req("GET","/api/negocios/mio",null,c.token())).get("puedeRegistrar").asBoolean()).isTrue();
        var r=req("POST","/api/negocios",negocio(true),c.token());assertThat(r.statusCode()).isEqualTo(201);
        assertThat(json(r).get("mensaje").asText()).contains("creado exitosamente");
        var datos=json(r).get("datos");assertThat(datos.get("idProveedor").asLong()).isEqualTo(c.id());
        assertThat(datos.get("direccion").isNull()).isTrue();assertThat(datos.get("galeria").size()).isEqualTo(2);
        var mio=json(req("GET","/api/negocios/mio",null,c.token()));
        assertThat(mio.get("puedeRegistrar").asBoolean()).isFalse();assertThat(mio.get("negocio").get("galeria").size()).isEqualTo(2);
        assertThat(req("POST","/api/negocios",negocio(true),c.token()).statusCode()).isEqualTo(409);
    }

    @Test void negocioValidaObligatoriosFormatosYPermisos() throws Exception {
        var p=cuenta(true);var cliente=cuenta(false);
        assertThat(req("POST","/api/negocios",negocio(true),null).statusCode()).isEqualTo(401);
        assertThat(req("POST","/api/negocios",negocio(true),cliente.token()).statusCode()).isEqualTo(403);
        var vacio=req("POST","/api/negocios",Map.of(),p.token());assertThat(vacio.statusCode()).isEqualTo(400);
        assertThat(json(vacio).get("campos").has("nombre")).isTrue();
        var body=negocio(false);body.remove("direccion");assertThat(req("POST","/api/negocios",body,p.token()).statusCode()).isEqualTo(400);
        for(var entry:Map.of("correo","invalido","numContacto","abc","fotoPrincipal","javascript:alert(1)").entrySet()) {
            body=negocio(true);body.put(entry.getKey(),entry.getValue());assertThat(req("POST","/api/negocios",body,p.token()).statusCode()).isEqualTo(400);
        }
        body=negocio(true);body.put("galeria",List.of(Map.of("url","https://example.com/a","tipo","OTRO")));
        assertThat(req("POST","/api/negocios",body,p.token()).statusCode()).isEqualTo(400);
        assertThat(req("POST","/api/negocios",negocio(false),p.token()).statusCode()).isEqualTo(201);
    }

    @Test void servicioSeAsociaYListaConPrecioCero() throws Exception {
        var c=cuenta(true);long id=crearNegocio(c);String ruta="/api/negocios/"+id+"/servicios";
        var body=servicio();body.put("precio",0);var r=req("POST",ruta,body,c.token());
        assertThat(r.statusCode()).isEqualTo(201);var datos=json(r).get("datos");
        assertThat(datos.get("idNegocio").asLong()).isEqualTo(id);assertThat(datos.get("estado").asText()).isEqualTo("ACTIVO");
        assertThat(datos.get("imagenReferencia").asText()).isEqualTo(body.get("imagenReferencia"));
        var lista=req("GET",ruta,null,c.token());assertThat(lista.statusCode()).isEqualTo(200);assertThat(json(lista).size()).isEqualTo(1);
    }

    @Test void servicioValidaNumerosCamposYNegocioPropio() throws Exception {
        var c=cuenta(true);var otro=cuenta(true);var cliente=cuenta(false);long id=crearNegocio(c);
        String ruta="/api/negocios/"+id+"/servicios";
        assertThat(req("POST",ruta,servicio(),otro.token()).statusCode()).isEqualTo(403);
        assertThat(req("GET",ruta,null,otro.token()).statusCode()).isEqualTo(403);
        assertThat(req("POST",ruta,servicio(),cliente.token()).statusCode()).isEqualTo(403);
        assertThat(req("POST",ruta,servicio(),null).statusCode()).isEqualTo(401);
        assertThat(req("POST","/api/negocios/99999999/servicios",servicio(),c.token()).statusCode()).isEqualTo(404);
        for(String campo:List.of("nombre","descripcion","duracionMinutos","precio")) {
            var body=servicio();body.remove(campo);assertThat(req("POST",ruta,body,c.token()).statusCode()).isEqualTo(400);
        }
        for(int duracion:List.of(0,-1)) { var body=servicio();body.put("duracionMinutos",duracion);assertThat(req("POST",ruta,body,c.token()).statusCode()).isEqualTo(400); }
        var fraccion=servicio();fraccion.put("duracionMinutos",1.5);assertThat(req("POST",ruta,fraccion,c.token()).statusCode()).isEqualTo(400);
        for(String precio:List.of("-1","0.001","10000000000")) {
            var body=servicio();body.put("precio",new java.math.BigDecimal(precio));assertThat(req("POST",ruta,body,c.token()).statusCode()).isEqualTo(400);
        }
        var body=servicio();body.put("imagenReferencia","http://example.com/no-seguro.jpg");assertThat(req("POST",ruta,body,c.token()).statusCode()).isEqualTo(400);
        assertThat(json(req("GET",ruta,null,c.token())).size()).isZero();
    }

    @Test void concurrenciaCorreoYNegocioMantienenUnicidad() throws Exception {
        var a=registro(false);var b=registro(true);b.put("correo",a.get("correo"));
        try(var executor=Executors.newFixedThreadPool(2)) {
            var gate=new CountDownLatch(1);
            var x=executor.submit(() -> {gate.await();return req("POST","/api/clientes",a,null).statusCode();});
            var y=executor.submit(() -> {gate.await();return req("POST","/api/proveedores",b,null).statusCode();});
            gate.countDown();assertThat(List.of(x.get(),y.get())).containsExactlyInAnyOrder(201,409);
            assertThat(jdbc.queryForObject("select count(*) from correos_registrados where correo=?",Integer.class,a.get("correo"))).isEqualTo(1);
            int clientes=jdbc.queryForObject("select count(*) from clientes where correo=?",Integer.class,a.get("correo"));
            int proveedores=jdbc.queryForObject("select count(*) from proveedores where correo=?",Integer.class,a.get("correo"));
            assertThat(clientes+proveedores).isEqualTo(1);
            var c=cuenta(true);var start=new CountDownLatch(1);
            var n1=executor.submit(() -> {start.await();return req("POST","/api/negocios",negocio(true),c.token()).statusCode();});
            var n2=executor.submit(() -> {start.await();return req("POST","/api/negocios",negocio(true),c.token()).statusCode();});
            start.countDown();assertThat(List.of(n1.get(),n2.get())).containsExactlyInAnyOrder(201,409);
            assertThat(jdbc.queryForObject("select count(*) from negocios where id_proveedor=?",Integer.class,c.id())).isEqualTo(1);
        }
    }

    @Test void jwtAlteradoVencidoEmisorYAudienciaInvalidos() throws Exception {
        var c=cuenta(false);String ruta="/api/clientes/"+c.id();String[] parts=c.token().split("\\.");
        parts[2]=(parts[2].startsWith("A")?"B":"A")+parts[2].substring(1);
        assertThat(req("GET",ruta,null,String.join(".",parts)).statusCode()).isEqualTo(401);
        Instant now=Instant.now();
        for(String caso:List.of("expired","issuer","audience","noexp","noaud")) {
            var builder=JwtClaimsSet.builder().subject("cliente:"+c.id()).issuedAt(now.minusSeconds(120)).issuer(caso.equals("issuer")?"otro":"bookingnow-api");
            if(!caso.equals("noaud"))builder.audience(List.of(caso.equals("audience")?"otra":"bookingnow"));
            if(!caso.equals("noexp"))builder.expiresAt(caso.equals("expired")?now.minusSeconds(5):now.plusSeconds(300));
            String token=encoder.encode(JwtEncoderParameters.from(JwsHeader.with(MacAlgorithm.HS256).build(),builder.build())).getTokenValue();
            assertThat(req("GET",ruta,null,token).statusCode()).isEqualTo(401);
        }
    }

    @Test void healthYSwaggerDocumentanSprint() throws Exception {
        assertThat(req("GET","/health",null,null).statusCode()).isEqualTo(200);
        assertThat(req("GET","/swagger-ui/index.html",null,null).statusCode()).isEqualTo(200);
        var r=req("GET","/v3/api-docs",null,null);assertThat(r.statusCode()).isEqualTo(200);assertThat(r.body()).doesNotContain("contrasenaHash");
        var api=json(r);assertThat(api.at("/components/securitySchemes/bearerAuth/scheme").asText()).isEqualTo("bearer");
        for(String ruta:List.of("/api/auth/login","/api/negocios","/api/negocios/mio","/api/negocios/{idNegocio}/servicios")) assertThat(api.get("paths").has(ruta)).isTrue();
        assertThat(api.get("paths").has("/api/auth/csrf")).isFalse();
    }

    @Test void corsPermiteFrontendsLocales() throws Exception {
        var request = HttpRequest.newBuilder(URI.create("http://localhost:" + port + "/api/auth/login"))
                .method("OPTIONS", HttpRequest.BodyPublishers.noBody())
                .header("Origin", "http://localhost:5173")
                .header("Access-Control-Request-Method", "POST")
                .build();
        var response = http.send(request, HttpResponse.BodyHandlers.ofString());
        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(response.headers().firstValue("access-control-allow-origin")).contains("http://localhost:5173");

        var health = HttpRequest.newBuilder(URI.create("http://localhost:" + port + "/health"))
                .header("X-Request-Id", "frontend-prueba-123")
                .GET().build();
        var healthResponse = http.send(health, HttpResponse.BodyHandlers.ofString());
        assertThat(healthResponse.headers().firstValue("x-request-id")).contains("frontend-prueba-123");
    }
}
