package com.example.book_store_back.catalog.application.usecases.book;

import java.util.UUID;

import com.example.book_store_back.catalog.application.dtos.book.RegisterBookCommand;

public interface RegisterBookUseCase {
    UUID execute(RegisterBookCommand command);
}
