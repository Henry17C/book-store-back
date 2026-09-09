package com.example.book_store_back.identity.application.dtos;

public record RegisterLocalUserCommand(
        String email,
        String fullName,
        String password) {

}
