package com.example.book_store_back.catalog.infrastructure.persistence.adapters;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.example.book_store_back.catalog.application.ports.ReviewRepository;
import com.example.book_store_back.catalog.domain.Review;
import com.example.book_store_back.catalog.infrastructure.persistence.entities.ReviewEntity;
import com.example.book_store_back.catalog.infrastructure.persistence.jpa.SpringDataReview;
import com.example.book_store_back.catalog.infrastructure.persistence.mappers.ReviewMapper;

@Component
public class ReviewRepositoryAdapter implements ReviewRepository {

    private final SpringDataReview jpaRepository;
    private final ReviewMapper mapper;

    public ReviewRepositoryAdapter(SpringDataReview jpaRepository, ReviewMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;

    }

    @Override
    public Optional<Review> findById(UUID id) {

        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public void save(Review review) {
        ReviewEntity entity = mapper.toEntity(review);
        jpaRepository.save(entity);
    }

    @Override
    public void delete(UUID id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public List<Review> findByBookId(UUID id) {

        return jpaRepository.findByBookId(id).stream().map(mapper::toDomain).toList();

    }

}
