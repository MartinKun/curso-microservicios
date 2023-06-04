package com.msvc.order.service;

import brave.Span;
import brave.Tracer;
import com.msvc.order.config.rabbitmq.Producer;
import com.msvc.order.controller.external.InventoryResponse;
import com.msvc.order.controller.request.OrderLineItemsDTO;
import com.msvc.order.controller.request.OrderRequest;
import com.msvc.order.event.OrderPlacedEvent;
import com.msvc.order.model.Order;
import com.msvc.order.model.OrderLineItems;
import com.msvc.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;

    private final OrderRepository orderRepository;
    private final WebClient.Builder webClientBuilder;

    private final Tracer tracer;

    private final Producer producer;

    public String placeOrder(OrderRequest orderRequest) {
        String orderNumber = UUID.randomUUID().toString();
        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDTOList()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
        Order order = Order.builder()
                .orderNumber(orderNumber)
                .orderLineItems(orderLineItems)
                .build();

        List<String> skuCodeList = order.getOrderLineItems().stream()
                .map(OrderLineItems::getSkuCode)
                .collect(Collectors.toList());

        Span inventoryServiceLookup = tracer.nextSpan().name("InventoryServiceLookup");

        try (Tracer.SpanInScope isLookup = tracer.withSpanInScope(inventoryServiceLookup.start())) {
            inventoryServiceLookup.tag("call", "inventory-service");

            InventoryResponse[] inventoryResponseArray = webClientBuilder.build().get()
                    .uri(
                            "http://inventory-service/api/v1/inventory",
                            uriBuilder -> uriBuilder.queryParam("skuCode", skuCodeList)
                                    .build()
                    )
                    .retrieve()
                    .bodyToMono(InventoryResponse[].class)
                    .block();

            boolean allProductsInStock = Arrays.stream(inventoryResponseArray)
                    .allMatch(InventoryResponse::isInStock);

            if (allProductsInStock) {
                orderRepository.save(order);
                sendMessageWithRabbitMq("Notificacion con rabbitMq, Pedido enviado con éxito");
                kafkaTemplate.send(
                        "notificationTopic",
                        new OrderPlacedEvent(order.getOrderNumber())
                );
                return "Pedido ordenado con éxito.";
            } else {
                throw new IllegalArgumentException("El producto no está en stock");
            }

        } finally {
            inventoryServiceLookup.flush();
        }

    }

    private void sendMessageWithRabbitMq(String message) {
        log.info("El mensaje '{}' ha sido enviado con éxito", message);
        producer.send(message);
    }

    public OrderLineItems mapToDto(OrderLineItemsDTO orderLineItemsDTO) {
        return OrderLineItems.builder()
                .price(orderLineItemsDTO.getPrice())
                .skuCode(orderLineItemsDTO.getSkuCode())
                .quantity(orderLineItemsDTO.getQuantity())
                .price(orderLineItemsDTO.getPrice())
                .build();
    }
}
