package co.edu.udea.bookingnow.infrastructure.config;

import com.nimbusds.jose.jwk.source.ImmutableSecret;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.time.Duration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.core.*;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import co.edu.udea.bookingnow.application.port.out.ClienteRepositoryPort;
import co.edu.udea.bookingnow.application.port.out.ProveedorRepositoryPort;
import co.edu.udea.bookingnow.application.port.out.UsuarioRepositoryPort;

@Configuration
public class JwtConfig {
    @Bean
    SecretKey jwtKey(@Value("${security.jwt.secret}") String secret) {
        byte[] bytes;
        try { bytes = Base64.getDecoder().decode(secret); }
        catch (IllegalArgumentException exception) { throw new IllegalArgumentException("JWT_SECRET debe ser Base64 valido"); }
        if (bytes.length < 32) { throw new IllegalArgumentException("JWT_SECRET requiere al menos 32 bytes aleatorios en Base64"); }
        return new SecretKeySpec(bytes, "HmacSHA256");
    }

    @Bean
    JwtEncoder jwtEncoder(SecretKey jwtKey) { return new NimbusJwtEncoder(new ImmutableSecret<>(jwtKey)); }

    @Bean
    JwtDecoder jwtDecoder(SecretKey jwtKey, @Value("${security.jwt.issuer}") String issuer,
            @Value("${security.jwt.audience}") String audience,
            ClienteRepositoryPort clientes, ProveedorRepositoryPort proveedores, UsuarioRepositoryPort usuarios) {
        var decoder = NimbusJwtDecoder.withSecretKey(jwtKey).macAlgorithm(MacAlgorithm.HS256).build();
        OAuth2TokenValidator<Jwt> claims = jwt -> {
            boolean valid = jwt.getExpiresAt() != null && jwt.getIssuedAt() != null
                    && jwt.getAudience() != null && jwt.getAudience().contains(audience);
            String subject = jwt.getSubject();
            if (subject == null || !subject.matches("(cliente|proveedor|usuario):[1-9][0-9]*")) { valid = false; }
            if (valid) {
                try {
                    long id = Long.parseLong(subject.substring(subject.indexOf(':') + 1));
                    valid = subject.startsWith("cliente:") ? clientes.obtenerPorId(id).filter(c -> c.getEstado() == co.edu.udea.bookingnow.domain.model.EstadoCuenta.ACTIVA).isPresent()
                            : subject.startsWith("proveedor:") ? proveedores.obtenerPorId(id).filter(c -> c.getEstado() == co.edu.udea.bookingnow.domain.model.EstadoCuenta.ACTIVA).isPresent()
                            : usuarios.obtenerPorId(id).filter(c -> c.getEstado() == co.edu.udea.bookingnow.domain.model.EstadoCuenta.ACTIVA).isPresent();
                } catch (NumberFormatException exception) { valid = false; }
            }
            return valid ? OAuth2TokenValidatorResult.success() : OAuth2TokenValidatorResult.failure(
                    new OAuth2Error("invalid_token", "Token o cuenta no validos", null));
        };
        decoder.setJwtValidator(new DelegatingOAuth2TokenValidator<>(
                new JwtTimestampValidator(Duration.ZERO), new JwtIssuerValidator(issuer), claims));
        return decoder;
    }
}
