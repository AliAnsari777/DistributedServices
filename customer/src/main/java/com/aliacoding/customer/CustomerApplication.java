package com.aliacoding.customer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(
        scanBasePackages = {
                "com.aliacoding.advancedmsgqueue",
                "com.aliacoding.customer"
        }
)
@EnableEurekaClient
@EnableFeignClients(
        basePackages = "com.aliacoding.clients"
)
public class CustomerApplication {
    public static void main(String[] args) {
        SpringApplication.run(CustomerApplication.class, args);
    }
}
