package com.aliacoding.customer.services;

import com.aliacoding.clients.fraud.FraudCheckResponse;
import com.aliacoding.clients.fraud.FraudClient;
import com.aliacoding.customer.entities.Customer;
import com.aliacoding.customer.entities.CustomerRegistrationRequest;
import com.aliacoding.customer.entities.NotificationRequest;
import com.aliacoding.customer.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public record CustomerService(CustomerRepository repository, RestTemplate restTemplate, FraudClient fraudClient) {

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

        // todo: send notification
        NotificationRequest notificationRequest = new
                NotificationRequest(customer.getId(), customer.getEmail(), "Success");
        String notification = restTemplate.postForObject(
                "http://NOTIFICATION/api/v1/notifications",
                notificationRequest,
                String.class
        );
    }
}
