package com.example.myapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.List;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();

        // Cho phép credentials (cookies, authorization headers)
        config.setAllowCredentials(true);

        // Danh sách origin được phép (frontend URLs)
        config.setAllowedOrigins(Arrays.asList(
                "http://localhost:3000", // React default
                "http://localhost:5173", // Vite default
                "http://localhost:8081", // Vue default
                "http://localhost:4200", // Angular default
                "http://127.0.0.1:3000",
                "http://127.0.0.1:5173",
                "http://127.0.0.1:8081"));

        // Cho phép tất cả HTTP methods
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));

        // Cho phép tất cả headers
        config.setAllowedHeaders(List.of("*"));

        // Headers được expose cho client
        config.setExposedHeaders(Arrays.asList(
                "Authorization",
                "Content-Type",
                "X-Total-Count"));

        // Thời gian cache preflight request (giây)
        config.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }
}
