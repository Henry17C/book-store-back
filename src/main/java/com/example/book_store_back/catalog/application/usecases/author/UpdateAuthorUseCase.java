package com.example.book_store_back.catalog.application.usecases.author;

import java.util.UUID;

import com.example.book_store_back.catalog.application.dtos.author.UpdateAuthorCommand;

public interface UpdateAuthorUseCase {
    public void execute (UUID id, UpdateAuthorCommand command);
}
