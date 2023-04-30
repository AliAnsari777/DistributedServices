package com.aliacoding.customer.services;

import com.aliacoding.customer.entities.Customer;
import com.aliacoding.customer.entities.CustomerRegistrationRequest;
import org.springframework.stereotype.Service;

@Service
public record CustomerService() {

    public void registerCustomer(CustomerRegistrationRequest request) {
        Customer customer = Customer.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .build();
    }
}
