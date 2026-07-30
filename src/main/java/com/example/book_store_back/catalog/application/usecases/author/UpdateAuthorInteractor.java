package com.example.book_store_back.catalog.application.usecases.author;

import java.util.UUID;

import com.example.book_store_back.catalog.application.dtos.author.UpdateAuthorCommand;
import com.example.book_store_back.catalog.application.ports.AuthorRepository;
import com.example.book_store_back.catalog.domain.Author;

public class UpdateAuthorInteractor implements UpdateAuthorUseCase {
    private final AuthorRepository authorRepository;

    public UpdateAuthorInteractor(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public void execute(UUID id, UpdateAuthorCommand command) {

        if (command == null) {
            throw new IllegalArgumentException("El comando de autor no puede ser nulo.");
        }
        if (id == null) {
            throw new IllegalArgumentException("El id no puede ser nulo.");
        }

        Author author = authorRepository.findById(id).orElseThrow(() -> {
            throw new IllegalArgumentException("No se encontró un autor con el ID: \" + id");
        });

        author.updateAuthor(command.name(), command.biography());

        authorRepository.save(author);
    }

}
