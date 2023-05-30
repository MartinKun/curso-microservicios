package com.msvc.user.controller;

import com.msvc.user.controller.request.UserRequest;
import com.msvc.user.entity.User;
import com.msvc.user.service.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<User> saveUser(@RequestBody UserRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userService.saveUser(request));
    }

    @GetMapping
    public ResponseEntity<List<User>> listUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    int retryCount = 1;

    @GetMapping("/{id}")
    //@CircuitBreaker(name = "ratingHotelBreaker", fallbackMethod = "ratingHotelFallBack")
    @Retry(name = "ratingHotelService", fallbackMethod = "ratingHotelFallBack")
    public ResponseEntity<User> getUser(
            @PathVariable Long id
    ) {
        log.info("Listar un sólo usuario : UsuarioController");
        log.info("Cantidad de reintentos : {}", retryCount);
        retryCount++;
        return ResponseEntity.ok(userService.getUser(id));
    }

    public ResponseEntity<User> ratingHotelFallBack(Long id, Exception exception) {
        log.info("El respaldo se ejecuta porque el servicio está inactivo : ", exception.getMessage());
        User user = User.builder()
                .email("root1@gmail.com")
                .name("root")
                .information("Este usuario se crea por defecto cuando un servicio se cae")
                .id(1323123L)
                .build();
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

}
