package com.example.book_store_back.catalog.domain.events;

import java.util.UUID;

public record ReviewCreatedEvent(
    UUID reviewId,
    UUID bookId,
    Integer rating
) {
    
}
