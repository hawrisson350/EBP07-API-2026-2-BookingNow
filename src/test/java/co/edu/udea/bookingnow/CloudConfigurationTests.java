package co.edu.udea.bookingnow;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("cloud")
@SpringBootTest(properties = {
    "security.jwt.secret=MDEyMzQ1Njc4OWFiY2RlZjAxMjM0NTY3ODlhYmNkZWY=",
    "spring.datasource.url=jdbc:h2:mem:cloud;MODE=PostgreSQL;DB_CLOSE_DELAY=-1;INIT=RUNSCRIPT FROM 'classpath:cloud-schema.sql'",
    "spring.datasource.driver-class-name=org.h2.Driver",
    "spring.datasource.username=sa",
    "spring.datasource.password="
})
class CloudConfigurationTests {
    @Test
    void cloudStartsWithExternallyProvisionedSchema() {
        // El contexto ejecuta ddl-auto=validate contra un esquema preexistente.
    }
}
