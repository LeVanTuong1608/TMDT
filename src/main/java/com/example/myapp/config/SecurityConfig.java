package com.example.myapp.config;

import com.example.myapp.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Postman bắt buộc disable
                // ❌ JWT → không dùng session
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // ❌ Tắt cơ chế mặc định
                .formLogin(form -> form.disable())
                .httpBasic(basic -> basic.disable())

                // Thêm JWT filter
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)

                .authorizeHttpRequests(auth -> auth
                        // // Public APIs
                        // .requestMatchers("/api/register", "/api/login").permitAll()
                        // // Admin APIs - cần ROLE_ADMIN
                        // .requestMatchers("/api/admin/**").hasAuthority("ADMIN")
                        // // Protected APIs - cần authentication
                        // .requestMatchers("/api/users/**").authenticated()
                        // .anyRequest().authenticated());
                        .requestMatchers(
                                "/api/auth/**")
                        .permitAll()

                        .requestMatchers("/api/admin/**")
                        .hasAuthority("ADMIN")

                        .requestMatchers("/api/users/**")
                        .authenticated()

                        .anyRequest().authenticated());

        return http.build();
    }
}
