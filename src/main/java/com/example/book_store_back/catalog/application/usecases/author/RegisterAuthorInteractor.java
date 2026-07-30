package com.example.book_store_back.catalog.application.usecases.author;

import java.util.UUID;

import com.example.book_store_back.catalog.application.dtos.author.RegisterAuthorCommand;
import com.example.book_store_back.catalog.application.ports.AuthorRepository;
import com.example.book_store_back.catalog.domain.Author;

public class RegisterAuthorInteractor implements RegisterAuthorUseCase {
    private final AuthorRepository authorRepository;

    public RegisterAuthorInteractor(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public UUID execute(RegisterAuthorCommand command) {
        if (command == null) {
            throw new IllegalArgumentException("El comando de autor no puede ser nulo.");
        }

        UUID id = UUID.randomUUID();
        Author author = Author.register(id, command.name(), command.biography());

        authorRepository.save(author);
        return id;
    }

}
