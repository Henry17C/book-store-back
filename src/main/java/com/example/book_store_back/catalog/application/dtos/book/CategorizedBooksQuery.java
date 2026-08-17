package com.example.book_store_back.catalog.application.dtos.book;

import com.example.book_store_back.catalog.application.strategies.CategoryCode;

public record CategorizedBooksQuery(
        CategoryCode code,
        int page,
        int size,
        Boolean onlyInStock
) {}
