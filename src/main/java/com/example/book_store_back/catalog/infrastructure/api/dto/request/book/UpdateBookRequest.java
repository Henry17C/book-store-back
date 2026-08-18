package com.example.book_store_back.catalog.infrastructure.api.dto.request.book;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.example.book_store_back.catalog.application.dtos.book.MoneyCommand;
import com.example.book_store_back.catalog.application.dtos.book.UpdateBookCommand;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

public record UpdateBookRequest(

    @NotBlank(message = "El título no puede estar vacío ni contener solo espacios.")
    String title,

    @NotBlank(message = "El ISBN no puede estar vacío ni contener solo espacios.")
    String isbn,

    @NotNull(message = "El precio es obligatorio.")
    @Valid
    MoneyRequest price,

    @NotBlank(message = "El formato no puede estar vacío ni contener solo espacios.")
    String format,

    @NotEmpty(message = "Se debe asignar al menos un autor.")
    List<UUID> authorIds,

    @NotBlank(message = "El idioma no puede estar vacío ni contener solo espacios.")    
    String language,

    @NotNull(message = "La fecha de lanzamiento es obligatoria.")
    @PastOrPresent(message = "La fecha de lanzamiento no puede ser una fecha futura.")    
    LocalDateTime releaseDate,

    @NotBlank(message = "La descripción no puede estar vacía ni contener solo espacios.")
    String description
) {
    public UpdateBookCommand toCommand(){
        return new UpdateBookCommand(
            this.title,
            this.isbn,
            new MoneyCommand(this.price.amount(),this.price.currency() ),
            this.format,
            this.authorIds,
            this.language,
            this.releaseDate,
            this.description
        );
    }
}
