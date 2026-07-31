package com.example.book_store_back.catalog.application.listeners;

import com.example.book_store_back.catalog.application.ports.BookRepository;
import com.example.book_store_back.catalog.application.ports.ReviewRepository;
import com.example.book_store_back.catalog.domain.Book;
import com.example.book_store_back.catalog.domain.events.ReviewUpdatedEvent;

public class UpdateBookRatingOnReviewUpdatedListener {
    private final BookRepository bookRepository;

    public UpdateBookRatingOnReviewUpdatedListener(BookRepository bookRepository, ReviewRepository reviewRepository) {
        this.bookRepository = bookRepository;
    }

    // Método asíncrono
    public void handle(ReviewUpdatedEvent event) {

        // 1. Buscamos el libro afectado
        Book book = bookRepository.findById(event.bookId())
                .orElseThrow(() -> new RuntimeException("Libro no encontrado para actualizar el rating."));

        // 2. El libro aplica su lógica de negocio

        book.updateReview(event.previousRating(), event.newRating());

        // 3. Persistencia
        bookRepository.save(book);
    }

}
