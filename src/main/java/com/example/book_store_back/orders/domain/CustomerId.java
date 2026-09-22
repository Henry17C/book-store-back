package com.example.book_store_back.orders.domain;

public record CustomerId(String value) {
    public CustomerId {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException("El valor del id del usuario no puede ser nulo.");
        }
    }
}
