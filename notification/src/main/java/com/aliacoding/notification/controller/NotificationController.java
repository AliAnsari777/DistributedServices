package com.aliacoding.notification.controller;

import com.aliacoding.notification.entities.NotificationRequest;
import com.aliacoding.notification.services.NotificationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("api/v1/notifications")
@AllArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping
    public void sendNotification(@RequestBody NotificationRequest request){
        log.info("Sending the notification {}", request);
        notificationService.sendNotification(request);
    }
}
