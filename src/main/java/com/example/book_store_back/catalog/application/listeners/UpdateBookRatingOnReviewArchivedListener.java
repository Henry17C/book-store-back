package com.example.book_store_back.catalog.application.listeners;

import com.example.book_store_back.catalog.application.ports.BookRepository;
import com.example.book_store_back.catalog.domain.Book;
import com.example.book_store_back.catalog.domain.events.ReviewArchivedEvent;

public class UpdateBookRatingOnReviewArchivedListener {
    private final BookRepository bookRepository;

    public UpdateBookRatingOnReviewArchivedListener(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    // Método asíncrono
    public void handle(ReviewArchivedEvent event) {

        // 1. Buscamos el libro afectado
        Book book = bookRepository.findById(event.bookId()).orElseThrow(() -> {
            return new IllegalArgumentException("El libro no existe.");
        });

        // 2. El libro aplica su lógica de negocio
        book.removeReviewRating(event.previousRating());
        
        // 3. Persistencia
        bookRepository.save(book);

    }
}
