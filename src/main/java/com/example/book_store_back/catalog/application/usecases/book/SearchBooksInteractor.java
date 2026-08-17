package com.example.book_store_back.catalog.application.usecases.book;

import com.example.book_store_back.catalog.application.dtos.book.CatalogBookResult;
import com.example.book_store_back.catalog.application.dtos.book.PageResult;
import com.example.book_store_back.catalog.application.dtos.book.SearchBooksQuery;
import com.example.book_store_back.catalog.application.ports.BookQueryGateway;

public class SearchBooksInteractor implements SearchBooksUseCase {
    private final BookQueryGateway bookQueryGateway;

    public SearchBooksInteractor(BookQueryGateway bookQueryGateway) {
        this.bookQueryGateway = bookQueryGateway;
    }

    @Override
    public PageResult<CatalogBookResult> execute(SearchBooksQuery query) {
        String keyword = query.keyword();

        // 1. Validación: Si el usuario manda vacío, retornamos la primera página del
        // catálogo general
        if (keyword == null || keyword.trim().isEmpty()) {
            return bookQueryGateway.getCatalogPage(query.page(), query.size());
        }

        // 2. Limpiamos espacios innecesarios que el usuario pudo teclear por error
        String sanitizedKeyword = keyword.trim();

        // 3. Delegamos la búsqueda compleja a la base de datos
        return bookQueryGateway.searchByKeyword(sanitizedKeyword, query.page(), query.size());
    }
}