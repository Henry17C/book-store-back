package com.example.book_store_back.catalog.application.usecases.book;

import com.example.book_store_back.catalog.application.dtos.book.CatalogBookResult;
import com.example.book_store_back.catalog.application.dtos.book.PageResult;
import com.example.book_store_back.catalog.application.dtos.book.SearchBooksQuery;

public interface SearchBooksUseCase {
    PageResult<CatalogBookResult> execute(SearchBooksQuery request);

}
