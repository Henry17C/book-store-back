package com.example.book_store_back.catalog.domain;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

//Aggregate
public class Review {
    private UUID id;
    private UUID bookId;
    private UUID userId;
    private Integer rating;
    private String comment;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean isActive;

    public Review(UUID id, UUID bookId, UUID userId, Integer rating, String comment, LocalDateTime createdAt,
            Boolean isActive, LocalDateTime updatedAt) {
        validateRating(rating);
        validateComment(comment);
        this.id = Objects.requireNonNull(id, "El id no puede ser nulo.");
        this.bookId = Objects.requireNonNull(bookId, "El id del libro no puede ser nulo.");
        this.userId = Objects.requireNonNull(userId, "El id del usuario no puede ser nulo.");
        this.rating = rating;
        this.comment = comment;
        this.createdAt = Objects.requireNonNull(createdAt, "La fecha no puede ser nula.");
        this.isActive = isActive;
        this.updatedAt = updatedAt;
    }

    // Factory
    public static Review write(UUID id, UUID bookId, UUID userId, Integer rating, String comment) {
        return new Review(id, bookId, userId, rating, comment, LocalDateTime.now(), true, null);

    }

    public void edit(Integer newRating, String newComment) {
        validateComment(newComment);
        validateRating(newRating);
        this.comment = newComment;
        this.rating = newRating;
        this.updatedAt = LocalDateTime.now();

    }

    public void archiveReview() {
        this.isActive = false;
        this.updatedAt = LocalDateTime.now();
    }

    public void updateCommet(String newComment) {
        validateComment(newComment);
        this.comment = newComment;
        this.updatedAt = LocalDateTime.now();

    }

    public void updateRating(Integer newRating) {
        validateRating(newRating);
        this.rating = newRating;
        this.updatedAt = LocalDateTime.now();

    }

    // Valicadion de la lógica de dominio

    private void validateRating(Integer rating) {
        if (rating == null || rating < 1 || rating > 5) {
            throw new IllegalArgumentException("La calificación debe estar entre 1 y 5.");
        }

    }

    private void validateComment(String comment) {
        if (comment == null || comment.strip().isEmpty()) {
            throw new IllegalArgumentException("El comentario no puede estar vacío.");
        }

    }

    // Getters

    public UUID getId() {
        return this.id;
    }

    public UUID getBookId() {
        return this.bookId;
    }

    public UUID getUserId() {
        return this.userId;
    }

    public Integer getRating() {
        return this.rating;
    }

    public String getComment() {
        return this.comment;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return this.updatedAt;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

}
