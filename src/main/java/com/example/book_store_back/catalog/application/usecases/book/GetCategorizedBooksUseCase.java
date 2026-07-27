package com.example.book_store_back.catalog.application.usecases.book;

import com.example.book_store_back.catalog.application.dtos.book.CatalogBookResponse;
import com.example.book_store_back.catalog.application.dtos.book.CategorizedBooksQueryRequest;
import com.example.book_store_back.catalog.application.dtos.book.PageResult;

public interface GetCategorizedBooksUseCase {
    public PageResult<CatalogBookResponse> execute(CategorizedBooksQueryRequest request);
}
