package co.edu.udea.bookingnow.infrastructure.config;

import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class StartupLog {
    private static final Logger log = LoggerFactory.getLogger(StartupLog.class);
    private final Environment environment;
    private final String corsOrigins;

    public StartupLog(Environment environment, @Value("${cors.allowed-origins}") String corsOrigins) {
        this.environment = environment;
        this.corsOrigins = corsOrigins;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void applicationReady() {
        log.info("application_ready profiles={} cors_allowed_origins={}",
                Arrays.toString(environment.getActiveProfiles()), corsOrigins);
    }
}
