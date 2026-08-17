package com.example.book_store_back.catalog.infrastructure.api.dto.request.author;

import com.example.book_store_back.catalog.application.dtos.author.UpdateAuthorCommand;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateAuthorRequest(
    @NotBlank(message = "El nombre no puede estar vacío ni contener solo espacios.")
    @Size(max = 100, message = "El nombre no puede exceder los 100 caracteres.")
    String name,

    @NotBlank(message = "La biografía no puede estar vacía.")
    String biography
) {
    public UpdateAuthorCommand toCommand(){
        return new UpdateAuthorCommand(this.name, this.biography);
    }
}
