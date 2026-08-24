package com.example.book_store_back.catalog.infrastructure.api.dto.response.book;

import java.util.List;
import java.util.UUID;

public record BookDetailsResponse(
    UUID id,
    String title,
    String isbn,
    String format,
    List<String> authorNames,
    Double averageRating,
    String description,
    String coverUrl,
    MoneyResponse price,
    Boolean inStock
) {
    
}
