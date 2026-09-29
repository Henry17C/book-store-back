package com.example.book_store_back.shared.domain.events;

import java.time.LocalDateTime;

public record BookCreatedEvent(
    String productId,
    LocalDateTime occurredOn
) {
    public BookCreatedEvent(String productId) {
        this(productId, LocalDateTime.now());
    }
}
