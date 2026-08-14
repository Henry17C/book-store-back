package com.example.book_store_back.catalog.infrastructure.persistence.mappers;

import org.springframework.stereotype.Component;

import com.example.book_store_back.catalog.domain.Review;
import com.example.book_store_back.catalog.infrastructure.persistence.entities.ReviewEntity;
@Component
public class ReviewMapper  {
    public ReviewEntity  toEntity(Review review){
        if (review == null) return null; 
        return new ReviewEntity(review.getId(),review.getBookId(), review.getUserId(), review.getRating(), review.getComment(), review.getCreatedAt(), review.getUpdatedAt(), review.getIsActive());
    }

    public Review toDomain (ReviewEntity entity){
        if (entity == null) return null;
        return new Review(entity.getId(), entity.getBookId(), entity.getUserId(), entity.getRating(), entity.getComment(), entity.getCreateAt(), entity.getIsActive(), entity.getUpdatedAt());
    }
    
}
