package com.uade.tpo.maricafe_back.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.uade.tpo.maricafe_back.controllers.auth.AuthenticationRequest;
import com.uade.tpo.maricafe_back.controllers.auth.AuthenticationResponse;
import com.uade.tpo.maricafe_back.controllers.auth.RegisterRequest;
import com.uade.tpo.maricafe_back.controllers.config.JwtService;
import com.uade.tpo.maricafe_back.entity.Role;
import com.uade.tpo.maricafe_back.entity.User;
import com.uade.tpo.maricafe_back.exceptions.UserDuplicateException;
import com.uade.tpo.maricafe_back.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        // Verificar si ya existe un usuario con este email
        if (repository.findByEmail(request.getEmail()).isPresent()) {
            throw new UserDuplicateException(request.getEmail());
        }

        // Si no se especifica rol, asignar USER por defecto
        // Solo los admins se crean con rol específico a través del script
        Role userRole = (request.getRole() != null) ? request.getRole() : Role.USER;

        var user = User.builder()
                .firstName(request.getFirstname())
                .lastName(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(userRole)
                .build();

        repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .user(user)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()));

        var user = repository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .user(user)
                .build();
    }
}
