package com.example.book_store_back.catalog.application.dtos.book;

import java.util.List;
import java.util.UUID;

import com.example.book_store_back.catalog.application.dtos.money.MoneyResponse;

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
