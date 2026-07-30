package com.example.book_store_back.catalog.application.dtos.author;

import java.util.UUID;

public record RegisterAuthorCommand(
        String name,
        String biography) {
}
