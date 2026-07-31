package com.example.book_store_back.catalog.application.usecases.review;

import java.util.UUID;

import com.example.book_store_back.catalog.application.dtos.review.UpdateReviewCommand;

public interface UpdateReviewUseCase {
    public void execute(UUID reviewId,UpdateReviewCommand command);
}
