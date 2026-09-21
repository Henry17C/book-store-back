package com.example.book_store_back.inventory.infrastructure.api.dto.response;

import java.util.List;

public record PageResponse<T>(
    List<T> content,
    int currentPage,
    int totalPages,
    long totalElements
) {
    
}
