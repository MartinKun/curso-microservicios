package com.msvc.inventory;

import com.msvc.inventory.model.Inventory;
import com.msvc.inventory.repository.InventoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaClient
public class InventoryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(InventoryServiceApplication.class, args);
    }

    @Bean
    public CommandLineRunner loadData(InventoryRepository inventoryRepository) {
        return args -> {
            Inventory newInventory = Inventory.builder()
                    .skuCode("iphone_12")
                    .quantity(100)
                    .build();

            Inventory newInventory2 = Inventory.builder()
                    .skuCode("iphone_12_blue")
                    .quantity(0)
                    .build();

            inventoryRepository.save(newInventory);
            inventoryRepository.save(newInventory2);
        };
    }

}
