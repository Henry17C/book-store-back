package com.example.book_store_back.orders.domain;

import java.util.Objects;
import java.util.UUID;

public record OrderId(UUID value) {
    public OrderId {
        Objects.requireNonNull(value, "El id de la orden no puede ser nulo.");
    }

    public static OrderId generate() {
        return new OrderId(UUID.randomUUID());
    }
}