package com.msvc.order.controller;


import com.msvc.order.controller.request.OrderRequest;
import com.msvc.order.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @CircuitBreaker(name = "inventory", fallbackMethod = "fallBackMethod")
    @TimeLimiter(name = "inventory")
    @Retry(name = "inventory")
    public CompletableFuture<String> placeAnOrder(@RequestBody OrderRequest request) {
        return CompletableFuture.supplyAsync(() ->orderService.placeOrder(request));
    }

    public CompletableFuture<String> fallBackMethod(OrderRequest orderRequest, RuntimeException ex){
        return CompletableFuture.supplyAsync(() -> "Oops! Ha ocurrido un error al realizar el pedido");
    }
}
