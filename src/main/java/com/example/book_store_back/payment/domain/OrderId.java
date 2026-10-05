package com.example.book_store_back.payment.domain;
import java.util.UUID;

public record OrderId(UUID value) {
    public OrderId {
        if (value == null) {
            throw new IllegalArgumentException("El ID del pago no puede ser nulo");
        }
    }
}