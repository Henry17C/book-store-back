package com.example.book_store_back.catalog.domain.events;

import java.util.UUID;

public record ReviewArchivedEvent(
    UUID reviewID,
    UUID bookId,
    Integer previousRating
) {
    
}
