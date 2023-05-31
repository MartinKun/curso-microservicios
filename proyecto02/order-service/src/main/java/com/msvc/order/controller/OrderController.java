package com.msvc.order.controller;


import com.msvc.order.controller.request.OrderRequest;
import com.msvc.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String placeAnOrder(@RequestBody OrderRequest request) {
        orderService.placeOrder(request);
        return "pedido realizado con éxito.";
    }
}
