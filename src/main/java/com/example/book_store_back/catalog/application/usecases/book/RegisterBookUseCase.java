package com.example.book_store_back.catalog.application.usecases.book;

import com.example.book_store_back.catalog.application.dtos.book.RegisterBookCommand;

public interface RegisterBookUseCase {
    void execute(RegisterBookCommand command);
}
