package com.example.book_store_back.identity.infraestructure.api.dto.request;

import com.example.book_store_back.identity.application.dtos.SyncFirebaseUserCommand;

public record SyncFirebaseUserRequest(

    String firebaseUid,
    String email,
    String fullName,
    String picture
) {
    public SyncFirebaseUserCommand toCommand(){

        return new SyncFirebaseUserCommand(
            this.firebaseUid,
            this.email,
            this.fullName,
            this.picture
        );
    }
}
