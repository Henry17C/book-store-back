package com.example.book_store_back.catalog.application.usecases.review;
import java.util.UUID;

public interface ArchiveReviewUseCase {
    public void execute (UUID reviewId);
}
