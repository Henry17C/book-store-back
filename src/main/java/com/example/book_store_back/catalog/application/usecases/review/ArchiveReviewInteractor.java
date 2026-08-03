package com.example.book_store_back.catalog.application.usecases.review;

import java.util.UUID;

import com.example.book_store_back.catalog.application.ports.DomainEventPublisher;
import com.example.book_store_back.catalog.application.ports.ReviewRepository;
import com.example.book_store_back.catalog.domain.Review;
import com.example.book_store_back.catalog.domain.events.ReviewArchivedEvent;

public class ArchiveReviewInteractor implements ArchiveReviewUseCase {

    private final ReviewRepository reviewRepository;
    private final DomainEventPublisher publisher;

    public ArchiveReviewInteractor(ReviewRepository reviewRepository, DomainEventPublisher publisher) {
        this.reviewRepository = reviewRepository;
        this.publisher = publisher;
    }

    public void execute(UUID reviewId) {

        // 1. Buscar la review
        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> {
            return new IllegalArgumentException("La review no existe.");
        });

        // 2. Archivar la review
        review.archiveReview();

        //3. Crear el evento
        ReviewArchivedEvent event = new ReviewArchivedEvent(review.getId(), review.getBookId(), review.getRating());
        
        // 4. Publicar el evento
        publisher.publish(event);

        // 5. Persistir
        reviewRepository.save(review);

    }

}
