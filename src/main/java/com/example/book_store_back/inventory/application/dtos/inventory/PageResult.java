package com.example.book_store_back.inventory.application.dtos.inventory;

import java.util.List;

public record PageResult<T>(
    List<T> content,
    int currentPage,
    int totalPages,
    long totalElements
) {
    
}
