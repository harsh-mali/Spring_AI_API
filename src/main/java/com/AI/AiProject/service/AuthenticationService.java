package com.AI.AiProject.service;

import com.AI.AiProject.controller.auth.AuthenticationRequest;
import com.AI.AiProject.controller.auth.AuthenticationResponse;
import com.AI.AiProject.controller.auth.RegisterRequest;
import com.AI.AiProject.model.Role;
import com.AI.AiProject.model.User;
import com.AI.AiProject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        // Check if a user with this email already exists
        if (repository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already in use.");
        }

        // Prevent admin registration from the public endpoint
        if (request.getRole() == Role.ADMIN) {
            throw new IllegalArgumentException("Admin registration is not allowed from this endpoint.");
        }

        var user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();
        repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}