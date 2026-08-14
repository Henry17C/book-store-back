package com.example.book_store_back.catalog.infrastructure.api;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.book_store_back.catalog.application.usecases.review.ArchiveReviewUseCase;
import com.example.book_store_back.catalog.application.usecases.review.UpdateReviewUseCase;
import com.example.book_store_back.catalog.application.usecases.review.WriteReviewUseCase;
import com.example.book_store_back.catalog.infrastructure.api.dto.request.review.UpdateReviewRequest;
import com.example.book_store_back.catalog.infrastructure.api.dto.request.review.WriteReviewRequest;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/reviews")
public class ReviewController {
    private final ArchiveReviewUseCase archiveReviewUseCase;
    private final UpdateReviewUseCase updateReviewUseCase;
    private final WriteReviewUseCase writeReviewUseCase;

    public ReviewController(ArchiveReviewUseCase archiveReviewUseCase, UpdateReviewUseCase updateReviewUseCase,
        WriteReviewUseCase writeReviewUseCase
    ) {
        this.archiveReviewUseCase=archiveReviewUseCase;
        this.updateReviewUseCase= updateReviewUseCase;
        this.writeReviewUseCase= writeReviewUseCase;

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> archiveReview(@PathVariable UUID id) {
        archiveReviewUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateReview(@PathVariable UUID id, @Valid @RequestBody UpdateReviewRequest request) {
        updateReviewUseCase.execute(id, request.toCommand());
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<Void> writeReview(@Valid @RequestBody WriteReviewRequest request) {
        writeReviewUseCase.execute(request.toCommand());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
