package br.com.loginauth.infra.cors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.addAllowedOrigin("http://localhost:3000");
        corsConfiguration.addAllowedOrigin("http://localhost:3000");
        corsConfiguration.addAllowedOrigin("http://localhost:8081");
        corsConfiguration.addAllowedOrigin("exp://192.168.1.43:8081");
        corsConfiguration.setAllowedOrigins(Arrays.asList(
                "http://localhost:3000",
                "http://localhost:8081",
                "https://mediotec-plus.vercel.app",
                "https://mediotec-frontend-fqikv39no-miguelamaral254s-projects.vercel.app",
                "https://mediotec-plus-atd051rza-miguelamaral254s-projects.vercel.app",
                "http://192.168.1.43:8081"
        ));

        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(source);
    }
}
