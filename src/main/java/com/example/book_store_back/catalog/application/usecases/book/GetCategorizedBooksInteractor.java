package com.example.book_store_back.catalog.application.usecases.book;

import java.util.List;

import com.example.book_store_back.catalog.application.dtos.book.CatalogBookResponse;
import com.example.book_store_back.catalog.application.dtos.book.CategorizedBooksQueryRequest;
import com.example.book_store_back.catalog.application.dtos.book.PageResult;
import com.example.book_store_back.catalog.application.strategies.CategoryCode;
import com.example.book_store_back.catalog.application.strategies.CategoryStrategy;

public class GetCategorizedBooksInteractor implements GetCategorizedBooksUseCase {

    // 1. Inyectamos una LISTA con todas las estrategias que existan en el sistema
    private final List<CategoryStrategy> strategies;

    public GetCategorizedBooksInteractor(List<CategoryStrategy> strategies) {
        this.strategies = strategies;
    }

    @Override
    public PageResult<CatalogBookResponse> execute(CategorizedBooksQueryRequest request) {

        CategoryCode code = request.code();

        // 2. Buscamos la estrategia correcta
        CategoryStrategy categoryStrategy = strategies.stream()
                .filter(strategy -> strategy.supports(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Categoria no soportada."));

        // 3. Pedimos los libros a la BD.
        // Como la BD del Catálogo tiene la columna 'has_stock',
        // el DTO CatalogBookResponse ya viene con ese dato mapeado directamente desde
        // SQL.
        return categoryStrategy.fetch(request.page(), request.size(), request.onlyInStock());
    }
}
