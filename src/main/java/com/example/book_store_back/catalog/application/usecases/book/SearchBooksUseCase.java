package com.example.book_store_back.catalog.application.usecases.book;

import com.example.book_store_back.catalog.application.dtos.book.CatalogBookResponse;
import com.example.book_store_back.catalog.application.dtos.book.PageResult;
import com.example.book_store_back.catalog.application.dtos.book.SearchBooksQueryRequest;

public interface SearchBooksUseCase {
    PageResult<CatalogBookResponse> execute(SearchBooksQueryRequest request);

}
