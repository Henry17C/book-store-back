package com.example.book_store_back.catalog.application.usecases.book;

import com.example.book_store_back.catalog.application.dtos.book.BookDetailsResult;

public interface GetBookDetailsByIsbnUseCase {
    public BookDetailsResult execute  (String isbn);
}
