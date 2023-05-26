package com.aliacoding.customer.services;

import com.aliacoding.advancedmsgqueue.RabbitMQMessageProducer;
import com.aliacoding.clients.fraud.FraudCheckResponse;
import com.aliacoding.clients.fraud.FraudClient;
import com.aliacoding.clients.notification.NotificationClient;
import com.aliacoding.clients.notification.NotificationRequest;
import com.aliacoding.customer.entities.Customer;
import com.aliacoding.customer.entities.CustomerRegistrationRequest;
import com.aliacoding.customer.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public record CustomerService(CustomerRepository repository, RestTemplate restTemplate,
                              FraudClient fraudClient, NotificationClient notificationClient,
                              RabbitMQMessageProducer producer) {

    public void registerCustomer(CustomerRegistrationRequest request) {
        Customer customer = Customer.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .build();

        // todo: check if email is valid
        // todo: check if email is not taken
        repository.saveAndFlush(customer);

        // this is the method before using feign open
//        FraudCheckResponse fraudCheckResponse = restTemplate.getForObject(
//                "http://FRAUD/api/v1/fraud-check/{customerId}",
//                FraudCheckResponse.class,
//                customer.getId()
//        );

        FraudCheckResponse fraudCheckResponse = fraudClient.isFraudster(customer.getId());

        assert fraudCheckResponse != null;
        if (fraudCheckResponse.isFraudster()){
            throw new IllegalStateException("fraudster");
        }

        // this way we used restTemplate
//        NotificationRequest notificationRequest = new
//                NotificationRequest(customer.getId(), customer.getEmail(), "Success");
//        restTemplate.postForObject(
//                "http://NOTIFICATION/api/v1/notifications",
//                notificationRequest,
//                String.class
//        );

        NotificationRequest notificationRequest = new NotificationRequest(
                customer.getId(), customer.getEmail(), "Welcome to the club " + customer.getFirstName()
        );
//        notificationClient.SendNotification(notificationRequest);

        producer.publish(notificationRequest, "internal.exchange", "internal.notification.routing-key");
    }
}
