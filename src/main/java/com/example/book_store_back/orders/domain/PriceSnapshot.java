package com.example.book_store_back.orders.domain;
import java.math.BigDecimal;

//VO
public record PriceSnapshot(BigDecimal amount, String currency) {
    public PriceSnapshot {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("El precio no pueder ser negativo.");
        }
        if (currency == null || currency.isBlank()) {
            throw new IllegalArgumentException("La divisa no puede ser nula.");
        }
    }
}