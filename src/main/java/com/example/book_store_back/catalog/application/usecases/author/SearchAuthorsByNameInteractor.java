package com.example.book_store_back.catalog.application.usecases.author;

import java.util.List;

import com.example.book_store_back.catalog.application.dtos.author.AuthorDetailsResult;
import com.example.book_store_back.catalog.application.dtos.author.SearchAuthorQuery;
import com.example.book_store_back.catalog.application.ports.AuthorRepository;
import com.example.book_store_back.catalog.domain.Author;

public class SearchAuthorsByNameInteractor implements SearchAuthorsByNameUseCase {

    private final AuthorRepository authorRepository;
    private static final int MAX_RESULTS = 10;

    public SearchAuthorsByNameInteractor(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public List<AuthorDetailsResult> execute(SearchAuthorQuery query) {

        String name = query.name();
        // 1. Espacios en blanco
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("El término de búsqueda no puede estar vacío.");
        }
        // 2. Limpiamos el texto ingresado por el usuario.
        String sanitizedName = name.trim();

        // 3. Gateway de busqueda
        List<Author> authors = authorRepository.findByName(sanitizedName);

        // 4. Mapeo al DTO de salida

        return authors.stream().map((author) -> {
            return new AuthorDetailsResult(author.getId(), author.getName(), author.getBiography());
        }).toList();
    }

}
