package com.msvc.product.controller;

import com.msvc.product.controller.request.ProductRequest;
import com.msvc.product.controller.response.ProductResponse;
import com.msvc.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void saveProduct(
            @RequestBody ProductRequest request
    ) {
        productService.createProduct(request);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> listProducts() {
        return productService.getAllProducts();
    }
}
