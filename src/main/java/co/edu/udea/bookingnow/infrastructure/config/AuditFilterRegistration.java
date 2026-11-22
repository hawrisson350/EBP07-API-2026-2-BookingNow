package co.edu.udea.bookingnow.infrastructure.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuditFilterRegistration {
    @Bean
    FilterRegistrationBean<RequestLogFilter> disableServletRegistration(RequestLogFilter filter) {
        var registration = new FilterRegistrationBean<>(filter);
        registration.setEnabled(false);
        return registration;
    }
}
