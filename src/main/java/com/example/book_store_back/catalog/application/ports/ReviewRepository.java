package com.example.book_store_back.catalog.application.ports;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.example.book_store_back.catalog.domain.Review;

public interface ReviewRepository {
    public Optional<Review> findById(UUID id);
    public void save(Review review);
    public void delete(UUID id);
    public List<Review> findByBookId(UUID id);
}
