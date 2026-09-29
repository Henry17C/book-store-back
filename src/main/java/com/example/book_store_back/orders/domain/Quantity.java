package com.example.book_store_back.orders.domain;
// VO
public record Quantity(Integer value) {
    public Quantity {
        if ( value < 0) {
            throw new IllegalArgumentException("La cantidad no puede ser menor que 0");
        }
    }
}