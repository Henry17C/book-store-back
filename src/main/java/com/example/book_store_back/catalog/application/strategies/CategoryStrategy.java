package com.example.book_store_back.catalog.application.strategies;

import com.example.book_store_back.catalog.application.dtos.book.CatalogBookResponse;
import com.example.book_store_back.catalog.application.dtos.book.PageResult;

public interface CategoryStrategy {
    // 1. Preguntamos si esta estrategia maneja este código
    boolean supports(CategoryCode categoryCode);

    // 2. Ejecutamos la búsqueda
    PageResult<CatalogBookResponse> fetch(int page, int size, Boolean onlyInStock);
}
