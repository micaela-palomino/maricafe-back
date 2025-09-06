package com.uade.tpo.maricafe_back.controllers.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.uade.tpo.maricafe_back.entity.Role;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req -> req
                        // Auth público
                        .requestMatchers("/maricafe/auth/**").permitAll()

                        // Categorías
                        .requestMatchers(HttpMethod.GET, "/categories/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/categories/**").hasAuthority(Role.ADMIN.name())
                        .requestMatchers(HttpMethod.PUT, "/categories/**").hasAuthority(Role.ADMIN.name())
                        .requestMatchers(HttpMethod.DELETE, "/categories/**").hasAuthority(Role.ADMIN.name())

                        // Productos
                        .requestMatchers(HttpMethod.GET, "/products/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/products/**").hasAuthority(Role.ADMIN.name())
                        .requestMatchers(HttpMethod.PUT, "/products/**").hasAuthority(Role.ADMIN.name())
                        .requestMatchers(HttpMethod.DELETE, "/products/**").hasAuthority(Role.ADMIN.name())

                        // Órdenes
                        .requestMatchers(HttpMethod.POST, "/orders").hasAuthority(Role.USER.name())
                        .requestMatchers(HttpMethod.GET, "/orders/user").hasAuthority(Role.USER.name())
                        .requestMatchers(HttpMethod.GET, "/orders/{id}").hasAuthority(Role.ADMIN.name())
                        .requestMatchers(HttpMethod.DELETE, "/orders/{id}").hasAuthority(Role.ADMIN.name())

                        // Usuarios
                        .requestMatchers(HttpMethod.PUT, "/users/{userId}").hasAuthority(Role.USER.name())
                        .requestMatchers("/users/**").hasAuthority(Role.ADMIN.name())

                        // Descuentos
                        .requestMatchers("/discounts/**").hasAuthority(Role.ADMIN.name())

                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}