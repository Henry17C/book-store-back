package com.example.book_store_back.catalog.application.usecases.book;

import com.example.book_store_back.catalog.application.dtos.book.CatalogBookResult;
import com.example.book_store_back.catalog.application.dtos.book.CategorizedBooksQuery;
import com.example.book_store_back.catalog.application.dtos.book.PageResult;

public interface GetCategorizedBooksUseCase {
    public PageResult<CatalogBookResult> execute(CategorizedBooksQuery request);
}
