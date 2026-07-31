package com.example.book_store_back.catalog.domain.events;

import java.util.UUID;

public record ReviewUpdatedEvent(
        UUID reviewId,
        UUID bookId,
        Integer previousRating, 
        Integer newRating) {

}
