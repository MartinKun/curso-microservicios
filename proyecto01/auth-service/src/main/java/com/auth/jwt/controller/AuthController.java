package com.auth.jwt.controller;

import com.auth.jwt.controller.request.AuthRequest;
import com.auth.jwt.controller.request.NewUserRequest;
import com.auth.jwt.controller.request.ServiceRequest;
import com.auth.jwt.controller.response.AuthResponse;
import com.auth.jwt.entity.AuthUser;
import com.auth.jwt.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @RequestBody AuthRequest request
    ) {
        if (request == null)
            return ResponseEntity.badRequest().build();

        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/validate")
    public ResponseEntity<AuthResponse> validate(
            @RequestParam String token,
            @RequestBody ServiceRequest request
    ) {
        if (token == null)
            return ResponseEntity.badRequest().build();
        AuthResponse response = authService.validate(token, request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/create")
    public ResponseEntity<AuthUser> create(
            @RequestBody NewUserRequest request
    ) {
        if (request == null)
            return ResponseEntity.badRequest().build();
        AuthUser authUser = authService.save(request);
        return ResponseEntity.ok(authUser);
    }
}
