package com.example.book_store_back.catalog.infrastructure.api.dto.request.review;

import java.util.UUID;

import com.example.book_store_back.catalog.application.dtos.review.WriteReviewCommand;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record WriteReviewRequest(

        @NotNull(message = "El id del libro es obligatorio.") UUID bookId,

        @NotNull(message = "El id del usuario es obligatorio.") UUID userId,

        @NotNull(message = "La calificación es obligatoria.") @Min(value = 1, message = "La calificación mínima es 1.") @Max(value = 5, message = "La calificación máxima es 1.") Integer rating,

        @NotNull(message = "El comentario no puede estar vacio.") String comment

) {

    public WriteReviewCommand toCommand(){
        return new WriteReviewCommand(this.bookId, this.userId, this.rating, this.comment);
    }

}
