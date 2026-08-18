package com.example.book_store_back.catalog.infrastructure.events.listener;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import com.example.book_store_back.catalog.application.listeners.UpdateBookRatingOnReviewArchivedListener;
import com.example.book_store_back.catalog.application.listeners.UpdateBookRatingOnReviewCreatedListener;
import com.example.book_store_back.catalog.application.listeners.UpdateBookRatingOnReviewUpdatedListener;
import com.example.book_store_back.catalog.domain.events.ReviewArchivedEvent;
import com.example.book_store_back.catalog.domain.events.ReviewCreatedEvent;
import com.example.book_store_back.catalog.domain.events.ReviewUpdatedEvent;

@Component
public class SpringReviewEventListenerAdapter {

    private final UpdateBookRatingOnReviewArchivedListener archivedListener;
    private final UpdateBookRatingOnReviewCreatedListener createdListener;
    private final UpdateBookRatingOnReviewUpdatedListener updatedListener;

    public SpringReviewEventListenerAdapter(
            UpdateBookRatingOnReviewArchivedListener archivedListener,
            UpdateBookRatingOnReviewCreatedListener createdListener,
            UpdateBookRatingOnReviewUpdatedListener updatedListener

    ) {
        this.archivedListener = archivedListener;
        this.createdListener = createdListener;
        this.updatedListener = updatedListener;
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onReviewCreated(ReviewCreatedEvent event) {
        createdListener.handle(event);
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onReviewArchived(ReviewArchivedEvent event) {
        archivedListener.handle(event);
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onReviewUpdated(ReviewUpdatedEvent event) {
        updatedListener.handle(event);
    }
}
