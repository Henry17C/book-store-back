package com.example.book_store_back.identity.application.dtos;

public record UserResult(
    String id,
    String email,
    String fullName,
    String role,
    String status,
    String picture
) {
    
}
