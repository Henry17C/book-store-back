package com.example.book_store_back.orders.domain;
// VO

import java.math.BigDecimal;

public record Quantity(BigDecimal value) {
    public Quantity {
        if ( value.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("La cantidad no puede ser menor que 0");
        }
    }
}