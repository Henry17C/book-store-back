package com.example.book_store_back.catalog.infrastructure.api.dto.request.review;

import java.util.UUID;

import com.example.book_store_back.catalog.application.dtos.review.UpdateReviewCommand;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record UpdateReviewRequest(

        @NotNull(message = "El id del libro es obligatorio") UUID bookId,

        @NotNull(message = "El id del usuario es obligatorio.") UUID userId,

        @NotNull(message = "La calificación es obligatoria.") @Min(value = 1, message = "La calificación mínima es 1.") @Max(value = 5, message = "La calificación máxima es 5.") Integer rating,

        @NotNull(message = "El comentario no puede estar vacio.") String comment) {
    public UpdateReviewCommand toCommand() {
        return new UpdateReviewCommand(this.bookId, this.userId, this.rating, this.comment);
    }
}
