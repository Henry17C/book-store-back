package com.example.book_store_back.catalog.application.usecases.review;

import java.util.UUID;

import com.example.book_store_back.catalog.application.dtos.review.UpdateReviewCommand;
import com.example.book_store_back.catalog.application.ports.DomainEventPublisher;
import com.example.book_store_back.catalog.application.ports.ReviewRepository;
import com.example.book_store_back.catalog.domain.Review;
import com.example.book_store_back.catalog.domain.events.ReviewUpdatedEvent;

public class UpdateReviewInteractor implements UpdateReviewUseCase {
    private final ReviewRepository reviewRepository;

    private final DomainEventPublisher domainEventPublisher;

    public UpdateReviewInteractor(ReviewRepository reviewRepository,
            DomainEventPublisher domainEventPublisher) {
        this.reviewRepository = reviewRepository;
        this.domainEventPublisher = domainEventPublisher;
    }

    public void execute(UUID reviewId, UpdateReviewCommand command) {
        if (command == null) {
            throw new IllegalArgumentException("El comando no puede ser nulo.");

        }

        /*
         * TODO: Integración futura con otros Bounded Contexts:
         * 1. Llamar al módulo de Usuarios para validar que el userId sea válido.
         * 2. Llamar al módulo de Órdenes (Orders) para verificar si este usuario
         * realmente compró el libro (para ponerle el badge de "Compra Verificada").
         */

        // 1. Validar que la review realmente exista
        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> {
            return new IllegalArgumentException("La review no existe.");
        });

        // 2. Capturar el estado ANTERIOR antes de que mute
        Integer previousRating = review.getRating();

        // 3. Editar la review
        review.edit(command.rating(), command.comment());

        // 4. Si el rating realmente cambió, disparamos el evento
        if (!previousRating.equals(command.rating())) {
            ReviewUpdatedEvent event = new ReviewUpdatedEvent(
                    review.getId(),
                    review.getBookId(), // Obtenemos el bookId de la fuente confiable: la BD
                    previousRating,
                    command.rating());
            domainEventPublisher.publish(event);
        }

        // 5. Persistir los cambios
        reviewRepository.save(review);

    }

}
