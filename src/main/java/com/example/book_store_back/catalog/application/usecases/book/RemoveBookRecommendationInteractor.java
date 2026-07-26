package com.example.book_store_back.catalog.application.usecases.book;

import java.util.UUID;

import com.example.book_store_back.catalog.application.ports.BookRepository;
import com.example.book_store_back.catalog.domain.Book;

public class RemoveBookRecommendationInteractor implements RemoveBookRecommendationUseCase {
    private final BookRepository bookRepository;

    public RemoveBookRecommendationInteractor(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public void execute(UUID bookId) {
        // 1. Buscar el libro
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("Libro no encontrado con el ID: " + bookId));
        // 2. Quitar la recomendacion el libro
        book.removeRecommendation();
        // 3. Guardar el cambio
        bookRepository.save(book);

    }
}
