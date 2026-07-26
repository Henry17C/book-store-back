package com.example.book_store_back.catalog.application.usecases.book;

import java.util.UUID;

import com.example.book_store_back.catalog.application.ports.BookRepository;
import com.example.book_store_back.catalog.domain.Book;

public class RecommendBookInteractor implements RecommendBookUseCase {

    private final BookRepository bookRepository;

    public RecommendBookInteractor(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public void execute(UUID bookId) {
        // 1. Buscar el libro
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("Libro no encontrado con el ID: " + bookId));
        // 2. Recomendar el libro
        book.markAsRecommended();
        // 3. Guardar el cambio
        bookRepository.save(book);

    }

}
