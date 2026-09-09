package com.example.book_store_back.identity.application.ports;

import java.util.Optional;

import com.example.book_store_back.identity.domain.User;
import com.example.book_store_back.identity.domain.UserId;

public interface UserRepository {
    public void save(User user);
    Optional<User> findById(UserId id);
    Optional<User> findByEmail(String email);

}
