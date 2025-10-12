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
import org.springframework.web.cors.CorsConfigurationSource;

import com.uade.tpo.maricafe_back.entity.Role;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final CustomAccessDeniedHandler accessDeniedHandler;
    private final CustomAuthenticationEntryPoint authenticationEntryPoint;
    private final CorsConfigurationSource corsConfigurationSource;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource))
                .authorizeHttpRequests(req -> req
                        // Auth público
                        .requestMatchers("/maricafe/auth/**").permitAll()

                        // Categorias
                        .requestMatchers(HttpMethod.GET, "/categories/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/categories/**").hasAuthority(Role.ADMIN.name())
                        .requestMatchers(HttpMethod.PUT, "/categories/**").hasAuthority(Role.ADMIN.name())
                        .requestMatchers(HttpMethod.DELETE, "/categories/**").hasAuthority(Role.ADMIN.name())

                        // Productos
                        .requestMatchers(HttpMethod.GET, "/products/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/products/**").hasAuthority(Role.ADMIN.name())
                        .requestMatchers(HttpMethod.PUT, "/products/**").hasAuthority(Role.ADMIN.name())
                        .requestMatchers(HttpMethod.DELETE, "/products/**").hasAuthority(Role.ADMIN.name())

                        // Ordenes
                        .requestMatchers(HttpMethod.POST, "/orders").hasAuthority(Role.USER.name())
                        .requestMatchers(HttpMethod.GET, "/orders/user").hasAuthority(Role.USER.name())
                        .requestMatchers(HttpMethod.GET, "/orders/{id}").hasAuthority(Role.ADMIN.name())
                        .requestMatchers(HttpMethod.GET, "/orders/active").hasAuthority(Role.ADMIN.name())
                        .requestMatchers(HttpMethod.GET, "/orders/inactive").hasAuthority(Role.ADMIN.name())
                        .requestMatchers(HttpMethod.DELETE, "/orders/{id}").hasAuthority(Role.ADMIN.name())

                        // Usuarios
                        .requestMatchers(HttpMethod.PUT, "/users/{userId}").hasAnyAuthority(Role.USER.name(), Role.ADMIN.name())
                        .requestMatchers(HttpMethod.GET, "/users").hasAuthority(Role.ADMIN.name())
                        .requestMatchers(HttpMethod.GET, "/users/{userId}").hasAuthority(Role.ADMIN.name())
                        .requestMatchers(HttpMethod.DELETE, "/users/{userId}").hasAuthority(Role.ADMIN.name())

                        // Descuentos
                        .requestMatchers("/discounts/**").hasAuthority(Role.ADMIN.name())

                        // Imagenes
                        .requestMatchers("/images").hasAuthority(Role.ADMIN.name())

                        .anyRequest().authenticated()
                )
                .exceptionHandling(ex -> ex
                        .accessDeniedHandler(accessDeniedHandler)
                        .authenticationEntryPoint(authenticationEntryPoint)
                )
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}