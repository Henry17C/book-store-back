package com.example.book_store_back.payment.domain;

import java.math.BigDecimal;
// Value Object
public record Money(BigDecimal amount, String currency) {
    public Money {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El monto del pago debe ser mayor a cero");
        }
        if (currency == null || currency.trim().isEmpty()) {
            throw new IllegalArgumentException("La moneda es obligatoria");
        }
    }
}