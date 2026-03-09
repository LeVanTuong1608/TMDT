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

    // @Bean
    // public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    // http
    // .csrf(csrf -> csrf.disable()) // Postman bắt buộc disable
    // // ❌ JWT → không dùng session
    // .sessionManagement(session ->
    // session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

    // // ❌ Tắt cơ chế mặc định
    // .formLogin(form -> form.disable())
    // .httpBasic(basic -> basic.disable())

    // // Thêm JWT filter
    // .addFilterBefore(jwtAuthenticationFilter,
    // UsernamePasswordAuthenticationFilter.class)

    // .authorizeHttpRequests(auth -> auth
    // // // Public APIs
    // // .requestMatchers("/api/register", "/api/login").permitAll()
    // // // Admin APIs - cần ROLE_ADMIN
    // // .requestMatchers("/api/admin/**").hasAuthority("ADMIN")
    // // // Protected APIs - cần authentication
    // // .requestMatchers("/api/users/**").authenticated()
    // // .anyRequest().authenticated());

    // // =============================
    // // Swagger
    // // =============================
    // .requestMatchers(
    // "/v3/api-docs/**",
    // "/swagger-ui/**",
    // "/swagger-ui.html")
    // .permitAll()
    // .requestMatchers(
    // "/api/auth/**")
    // .permitAll()

    // .requestMatchers("/api/admin/**")
    // .hasAuthority("ADMIN")

    // .requestMatchers("/api/users/**")
    // .authenticated()

    // .anyRequest().authenticated());
    // return http.build();
    // }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .formLogin(form -> form.disable())
                .httpBasic(basic -> basic.disable())

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html")
                        .permitAll()
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/api/admin/**").hasAuthority("ADMIN")
                        .requestMatchers("/api/users/**").authenticated()
                        .anyRequest().authenticated())

                // ✅ CHỈ GIỮ 1 LẦN Ở ĐÂY
                .addFilterBefore(jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
