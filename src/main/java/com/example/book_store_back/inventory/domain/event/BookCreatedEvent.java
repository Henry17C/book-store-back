package com.example.book_store_back.inventory.domain.event;

import java.time.LocalDateTime;

public record BookCreatedEvent(
    String productId,
    String title,
    String isbn,
    LocalDateTime createdAt
) {
    public BookCreatedEvent(String productId, String title, String isbn) {
        this(productId, title, isbn, LocalDateTime.now());
    }
}