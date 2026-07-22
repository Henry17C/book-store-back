package com.example.book_store_back.catalog.application.usecases.book;

import java.util.UUID;

import com.example.book_store_back.catalog.application.dtos.book.BookDetailsResponse;

public interface GetBookDetailsUseCase {
    BookDetailsResponse execute(UUID idBook);
}
