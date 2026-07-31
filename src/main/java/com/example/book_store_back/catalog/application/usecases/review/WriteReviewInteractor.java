package com.example.book_store_back.catalog.application.usecases.review;

import java.util.UUID;

import com.example.book_store_back.catalog.application.dtos.review.WriteReviewCommand;
import com.example.book_store_back.catalog.application.ports.BookRepository;
import com.example.book_store_back.catalog.application.ports.DomainEventPublisher;
import com.example.book_store_back.catalog.application.ports.ReviewRepository;
import com.example.book_store_back.catalog.domain.Review;
import com.example.book_store_back.catalog.domain.events.ReviewCreatedEvent;

public class WriteReviewInteractor implements WriteReviewUseCase {

    private final BookRepository bookRepository;
    private final ReviewRepository reviewRepository;
    private final DomainEventPublisher eventPublisher; 

    public WriteReviewInteractor(BookRepository bookRepository, ReviewRepository reviewRepository, DomainEventPublisher eventPublisher) {
        this.bookRepository = bookRepository;
        this.reviewRepository = reviewRepository;
        this.eventPublisher=eventPublisher;
    }

    public UUID execute(WriteReviewCommand command) {

        if (command == null) {
            throw new IllegalArgumentException("El comando no puede ser nulo.");

        }
        // 1. Validar que el libro realmente exista en el catálogo
        Boolean bookExists = bookRepository.existsById(command.bookId());

        if (!bookExists) {
            throw new IllegalArgumentException("El libro que intentas reseñar no existe.");

        }

        /*
         * TODO: Integración futura con otros Bounded Contexts:
         * 1. Llamar al módulo de Usuarios para validar que el userId sea válido.
         * 2. Llamar al módulo de Órdenes (Orders) para verificar si este usuario
         * realmente compró el libro (para ponerle el badge de "Compra Verificada").
         */

        // 2. Generar ID y crear el agregado mediante el Factory method
        UUID reviewId = UUID.randomUUID();

        Review review = Review.write(reviewId, command.bookId(), command.bookId(), command.rating(), command.comment());

        // 3. Persistir en la base de datos

        reviewRepository.save(review);

        // 4.  Publicamos el evento para quien le interese escucharlo
        ReviewCreatedEvent event = new ReviewCreatedEvent(reviewId, command.bookId(), command.rating());
        eventPublisher.publish(event);

        // 5. Return ID generado al frontend
        return reviewId;
    }

}
