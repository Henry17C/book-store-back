package com.example.book_store_back.catalog.application.usecases.author;

import java.util.UUID;

import com.example.book_store_back.catalog.application.dtos.author.RegisterAuthorCommand;

public interface RegisterAuthorUseCase {
    public UUID execute (RegisterAuthorCommand command);
}
