package com.example.book_store_back.catalog.infrastructure.api.dto.response.book;

import java.util.List;
import java.util.UUID;
public record CatalogBookResponse(
    UUID id,
    String title,
    String coverUrl,
    List<String> authorNames,
    MoneyResponse price,
    Double averageRating,
    Boolean inStock
) {
    
}
