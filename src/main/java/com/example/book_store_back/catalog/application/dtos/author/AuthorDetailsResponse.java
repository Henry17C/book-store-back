package com.example.book_store_back.catalog.application.dtos.author;

import java.util.UUID;

public record AuthorDetailsResponse(
        UUID id,
        String name,
        String biography) {

}
