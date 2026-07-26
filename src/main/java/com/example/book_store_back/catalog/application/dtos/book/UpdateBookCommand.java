package com.example.book_store_back.catalog.application.dtos.book;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.example.book_store_back.catalog.application.dtos.money.MoneyResponse;

public record UpdateBookCommand(
    String title,
    String isbn,
    MoneyResponse price,
    String format,
    List<UUID> authorIds,
    String languaje,
    LocalDateTime releaseDate,
    String description
) {
    
}
