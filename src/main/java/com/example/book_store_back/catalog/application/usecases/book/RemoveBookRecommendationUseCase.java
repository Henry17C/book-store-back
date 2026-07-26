package com.example.book_store_back.catalog.application.usecases.book;

import java.util.UUID;

public interface RemoveBookRecommendationUseCase {
    public void execute (UUID bookId);
}
