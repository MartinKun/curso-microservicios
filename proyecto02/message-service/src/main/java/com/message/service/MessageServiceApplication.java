package com.message.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class MessageServiceApplication {

    @RabbitListener(queues = {"${msvc.queue.name}"})
    public void getMessagesFromRabbitMq(String message) {
        log.info("Mensaje recibido {}", message);
    }

    public static void main(String[] args) {
        SpringApplication.run(MessageServiceApplication.class, args);
    }

}
