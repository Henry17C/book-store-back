package com.example.book_store_back.inventory.domain.event;

import java.time.LocalDateTime;

public record BookCreatedEvent(
    String productId,
    LocalDateTime createdAt
) {
    public BookCreatedEvent(String productId) {
        this(productId, LocalDateTime.now());
    }
}