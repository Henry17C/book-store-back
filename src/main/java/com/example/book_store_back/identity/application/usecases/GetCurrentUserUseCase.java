package com.example.book_store_back.identity.application.usecases;

import com.example.book_store_back.identity.application.dtos.UserResult;

public interface GetCurrentUserUseCase {
    public UserResult execute (String uid);
}
