package com.example.book_store_back.catalog.application.dtos.review;

import java.util.UUID;

public record UpdateReviewCommand(
        UUID bookId,
        UUID userId,
        Integer rating,
        String comment) {

}
