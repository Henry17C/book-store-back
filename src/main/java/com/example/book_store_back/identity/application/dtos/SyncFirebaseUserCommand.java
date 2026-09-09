package com.example.book_store_back.identity.application.dtos;

public record SyncFirebaseUserCommand(
    String firebaseUid,
    String email,
    String fullName,
    String pictureUrl
) {}
