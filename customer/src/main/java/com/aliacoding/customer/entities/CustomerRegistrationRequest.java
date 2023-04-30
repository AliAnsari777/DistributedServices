package com.aliacoding.customer.entities;

public record CustomerRegistrationRequest(
        String firstName,
        String lastName,
        String email) {
}
