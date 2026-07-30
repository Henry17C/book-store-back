package com.example.book_store_back.catalog.application.dtos.author;

public record UpdateAuthorCommand(
        String name,
        String biography) {

}
