package com.msvc.inventory.controller;

import com.msvc.inventory.controller.response.InventoryResponse;
import com.msvc.inventory.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponse> isInStock(
            @RequestParam("skuCode") List<String> skuCodeList
    ) {
        return inventoryService.isInStock(skuCodeList);
    }
}
