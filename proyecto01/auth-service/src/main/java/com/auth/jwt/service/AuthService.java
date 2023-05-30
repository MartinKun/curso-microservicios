package com.auth.jwt.service;

import com.auth.jwt.controller.request.AuthRequest;
import com.auth.jwt.controller.request.NewUserRequest;
import com.auth.jwt.controller.request.ServiceRequest;
import com.auth.jwt.controller.response.AuthResponse;
import com.auth.jwt.entity.AuthUser;
import com.auth.jwt.repository.AuthUserRepository;
import com.auth.jwt.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthUserRepository authUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public AuthUser save(NewUserRequest request) {
        Optional<AuthUser> user = authUserRepository.findByUsername(request.getUsername());

        if (user.isPresent())
            return null;

        String password = passwordEncoder.encode(request.getPassword());
        AuthUser authUser = AuthUser.builder()
                .username(request.getUsername())
                .password(password)
                .role(request.getRole())
                .build();

        return authUserRepository.save(authUser);
    }

    public AuthResponse login(AuthRequest dto) {
        Optional<AuthUser> user = authUserRepository.findByUsername(dto.getUsername());

        if (!user.isPresent())
            return null;

        if (passwordEncoder.matches(dto.getPassword(), user.get().getPassword())) {
            return AuthResponse.builder()
                    .token(jwtProvider.createToken(user.get()))
                    .build();
        }
        return null;
    }

    public AuthResponse validate(String token, ServiceRequest request) {
        if (!jwtProvider.validate(token, request))
            return null;

        String username = jwtProvider.getUserNameFromToken(token);

        if (!authUserRepository.findByUsername(username).isPresent())
            return null;

        return AuthResponse.builder()
                .token(token)
                .build();
    }
}
