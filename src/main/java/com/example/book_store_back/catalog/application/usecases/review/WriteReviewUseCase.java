package com.example.book_store_back.catalog.application.usecases.review;

import java.util.UUID;

import com.example.book_store_back.catalog.application.dtos.review.WriteReviewCommand;

public interface WriteReviewUseCase {
    public UUID execute(WriteReviewCommand command);
}
