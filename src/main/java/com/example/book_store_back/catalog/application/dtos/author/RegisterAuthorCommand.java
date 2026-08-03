package com.example.book_store_back.catalog.application.dtos.author;

public record RegisterAuthorCommand(
        String name,
        String biography) {
}
