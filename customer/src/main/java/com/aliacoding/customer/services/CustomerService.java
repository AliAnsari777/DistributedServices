package com.aliacoding.customer.services;

import com.aliacoding.customer.entities.Customer;
import com.aliacoding.customer.entities.CustomerRegistrationRequest;
import com.aliacoding.customer.repository.CustomerRepository;
import org.springframework.stereotype.Service;

@Service
public record CustomerService(CustomerRepository repository) {

    public void registerCustomer(CustomerRegistrationRequest request) {
        Customer customer = Customer.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .build();

        repository.save(customer);
    }
}
