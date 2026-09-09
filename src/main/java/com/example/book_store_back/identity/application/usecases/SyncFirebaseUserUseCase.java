package com.example.book_store_back.identity.application.usecases;

import com.example.book_store_back.identity.application.dtos.SyncFirebaseUserCommand;
import com.example.book_store_back.identity.application.dtos.UserResult;

public interface SyncFirebaseUserUseCase {
    public UserResult execute (SyncFirebaseUserCommand command);
}
