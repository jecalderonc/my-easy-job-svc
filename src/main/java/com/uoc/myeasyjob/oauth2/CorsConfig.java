package com.uoc.myeasyjob.oauth2;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * Configuration class to avoid the Cors filter.
 */
@Configuration
public class CorsConfig {

    private static final String ASTERISK = "*";
    private static final String PATH = "/**";

    /**
     * Configuration of the allowed request to the application endpoints.
     */
    @Bean
    public FilterRegistrationBean customCorsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin(ASTERISK);
        config.addAllowedHeader(ASTERISK);
        config.addAllowedMethod(ASTERISK);
        source.registerCorsConfiguration(PATH, config);
        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));

        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean;
    }
}
