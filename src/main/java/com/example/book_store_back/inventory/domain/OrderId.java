package com.example.book_store_back.inventory.domain;

// VO
// Usado unicamente dentro de Reservation
public record OrderId(String value) {
    public OrderId {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("El id de la orden no puede ser nulo o vacío.");
        }
    }
}