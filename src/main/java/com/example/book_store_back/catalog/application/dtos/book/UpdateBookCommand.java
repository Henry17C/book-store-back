package com.example.book_store_back.catalog.application.dtos.book;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record UpdateBookCommand(
    String title,
    String isbn,
    MoneyCommand price,
    String format,
    List<UUID> authorIds,
    String languaje,
    LocalDateTime releaseDate,
    String description
) {
    
}
