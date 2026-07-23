package com.example.book_store_back.catalog.application.usecases.book;

import java.util.UUID;

import com.example.book_store_back.catalog.application.dtos.book.UpdateBookCommand;

public interface UpdateBookUseCase {
    public void execute(UUID bookId,UpdateBookCommand command);
}
