package com.example.book_store_back.catalog.application.dtos.book;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record RegisterBookCommand(
    String title,
    String isbn,
    MoneyResul price,
    String format,
    List<UUID> authorIds,
    String language,
    LocalDateTime releaseDate,
    String description
) {
    
}
