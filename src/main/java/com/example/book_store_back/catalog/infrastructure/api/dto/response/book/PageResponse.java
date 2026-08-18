package com.example.book_store_back.catalog.infrastructure.api.dto.response.book;

import java.util.List;

public record PageResponse<T>(
    List<T> content,
    int currentPage,
    int totalPages,
    long totalElements
) {
    
}
