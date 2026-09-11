package com.example.book_store_back.identity.infraestructure.api.dto.response;

public record UserResponse(
    String id,
    String email,
    String fullName,
    String role,
    String status,
    String picture
) {
    
}
