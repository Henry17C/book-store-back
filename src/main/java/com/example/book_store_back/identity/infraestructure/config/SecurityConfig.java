package com.example.book_store_back.identity.infraestructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
                http
                                // 1. Deshabilitar CSRF (No necesario en APIs REST stateless)
                                .csrf(csrf -> csrf.disable())

                                // 2. Configurar la gestión de sesiones (Stateless)
                                .sessionManagement(session -> session
                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                                // 3. Proteger las rutas
                                .authorizeHttpRequests(auth -> auth
                                                // Dejar rutas las públicas necesarias (ejm. ver el catálogo de libros
                                                // sin loguearse)
                                                .requestMatchers("/books/**").permitAll()

                                                // Exigir autenticación para cualquier otra petición
                                                .anyRequest().authenticated())

                                // 4. Habilitar la validación automática de tokens JWT usando la URL de Firebase
                                .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> {
                                }));

                return http.build();
        }

        @Bean
        public JwtDecoder jwtDecoder() {
                return NimbusJwtDecoder
                                .withJwkSetUri(
                                                "https://www.googleapis.com/service_accounts/v1/jwk/securetoken@system.gserviceaccount.com")
                                .build();
        }

}