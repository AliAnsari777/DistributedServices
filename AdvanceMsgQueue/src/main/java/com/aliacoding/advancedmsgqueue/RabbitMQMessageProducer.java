package com.aliacoding.advancedmsgqueue;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class RabbitMQMessageProducer {

    private final AmqpTemplate amqpTemplate;

    // this is the method that allow us to publish to any exchange by providing the exchange name and routing key
    // we put payload as object because we want to be able to send JSON or XML or any other possible type.
    public void publish(String exchange, String routingKey, Object payload) {
        log.info("Publishing to {} using routing key {}. Payload {}", exchange, routingKey, payload);
        amqpTemplate.convertAndSend(exchange, routingKey, payload);
        log.info("Published to {} using routing key {}. Payload {}", exchange, routingKey, payload);
    }
}
