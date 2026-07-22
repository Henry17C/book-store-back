package com.example.book_store_back.catalog.application.dtos.book;

import java.util.List;

public record PageResult<T>(
    List<T> content,
    int currentPage,
    int totalPages,
    long totalElements
) {
    
}
