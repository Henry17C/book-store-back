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

    public Review(UUID id, UUID bookId, UUID userId, Integer rating, String comment, LocalDateTime createdAt) {
        validateRating(rating);
        validateComment(comment);
        this.id = Objects.requireNonNull(id, "El id no puede ser nulo.");
        this.bookId = Objects.requireNonNull(bookId, "El id del libro no puede ser nulo.");
        this.userId = Objects.requireNonNull(userId, "El id del usuario no puede ser nulo.");
        this.rating = rating;
        this.comment = comment;
        this.createdAt = Objects.requireNonNull(createdAt, "La fecha no puede ser nula.");
    }

    // Factory
    public static Review write(UUID id, UUID bookId, UUID userId, Integer rating, String comment) {
        return new Review(id, bookId, userId, rating, comment, LocalDateTime.now());

    }

    public void updateCommet(String newComment) {
        validateComment(comment);
        this.comment = newComment;

    }

    public void updateRating(Integer newRating) {
        validateRating(rating);
        this.rating = newRating;

    }

    // Valicadion de la lógica de dominio

    private void validateRating(Integer rating) {
        if (rating == null || rating < 1 || rating > 5) {
            throw new IllegalArgumentException("La calificación debe esta entre 1 y 5");
        }

    }

    private void validateComment(String comment) {
        if (comment == null || comment.strip().isEmpty()) {
            throw new IllegalArgumentException("El comentario no puede estar vacío");
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

    public LocalDateTime getCreateAt() {
        return this.createdAt;
    }

}
