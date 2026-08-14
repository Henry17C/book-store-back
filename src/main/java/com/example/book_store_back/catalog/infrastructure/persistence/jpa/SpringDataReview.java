package com.example.book_store_back.catalog.infrastructure.persistence.jpa;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.book_store_back.catalog.infrastructure.persistence.entities.ReviewEntity;

@Repository
public interface SpringDataReview extends JpaRepository<ReviewEntity, UUID> {
        public List<ReviewEntity> findByBookId(UUID id);

}
