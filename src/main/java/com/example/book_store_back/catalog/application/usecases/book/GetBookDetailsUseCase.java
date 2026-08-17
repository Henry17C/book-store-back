package com.example.book_store_back.catalog.application.usecases.book;

import java.util.UUID;

import com.example.book_store_back.catalog.application.dtos.book.BookDetailsResult;

public interface GetBookDetailsUseCase {
    BookDetailsResult execute(UUID idBook);
}
