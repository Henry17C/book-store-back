package com.example.book_store_back.catalog.application.usecases.book;

import java.util.UUID;

public interface  RecommendBookUseCase {
    public void execute(UUID bookId);
}
