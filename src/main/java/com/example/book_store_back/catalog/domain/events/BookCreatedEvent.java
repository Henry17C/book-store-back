package com.example.book_store_back.catalog.domain.events;

import java.time.LocalDateTime;

public record BookCreatedEvent(
    String productId,
    String title,
    LocalDateTime occurredOn
) {
    public BookCreatedEvent(String productId, String title) {
        this(productId, title, LocalDateTime.now());
    }
}