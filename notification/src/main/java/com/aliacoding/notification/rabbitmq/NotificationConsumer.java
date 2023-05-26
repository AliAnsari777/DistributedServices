package com.aliacoding.notification.rabbitmq;

import com.aliacoding.notification.entities.NotificationRequest;
import com.aliacoding.notification.services.NotificationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class NotificationConsumer {

    private final NotificationService notificationService;

    @RabbitListener(queues = "${rabbitmq.queue.notification}")
    public void consumer(NotificationRequest request) {
        log.info("consumed {} from queue", request);
        notificationService.sendNotification(request);
    }
}
