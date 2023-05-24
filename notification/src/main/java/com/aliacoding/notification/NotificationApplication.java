package com.aliacoding.notification;

import com.aliacoding.advancedmsgqueue.RabbitMQMessageProducer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(
        scanBasePackages = {
                "com.aliacoding.notification",
                "com.aliacoding.advancedmsgqueue"
        }
)
@EnableEurekaClient
@EnableFeignClients(
        basePackages = "com.aliacoding.clients"
)
public class NotificationApplication {
    public static void main(String[] args) {
        SpringApplication.run(NotificationApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(RabbitMQMessageProducer producer, NotificationConfig notificationConfig) {
        return args -> {
            producer.publish(new person("Ali A", 32),
                    notificationConfig.getInternalExchange(),
                    notificationConfig.getInternalNotificationRoutingKeys());
        };
    }

    record person(String name, int age) {}
}
