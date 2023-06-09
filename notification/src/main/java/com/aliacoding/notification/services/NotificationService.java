package com.aliacoding.notification.services;

import com.aliacoding.notification.entities.Notification;
import com.aliacoding.notification.entities.NotificationRequest;
import com.aliacoding.notification.repository.NotificationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public void sendNotification(NotificationRequest notificationRequest) {
        Notification notification = Notification.builder()
                .toCustomerId(notificationRequest.toCustomerId())
                .toCustomerEmail(notificationRequest.toCustomerEmail())
                .message(notificationRequest.message())
                .sender("Ali Ansari")
                .sentAt(LocalDateTime.now())
                .build();

        notificationRepository.save(notification);
    }
}
