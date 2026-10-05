package com.example.book_store_back.payment.domain;

import java.util.UUID;

// Value Object
public record PaymentId(UUID value) {
    public PaymentId {
        if (value == null) {
            throw new IllegalArgumentException("El ID del pago no puede ser nulo");
        }
    }

    public static PaymentId generateId() {
        return new PaymentId(UUID.randomUUID());
    }
}