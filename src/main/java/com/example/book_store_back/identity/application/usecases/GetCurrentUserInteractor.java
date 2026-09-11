package com.example.book_store_back.identity.application.usecases;

import com.example.book_store_back.identity.application.dtos.UserResult;
import com.example.book_store_back.identity.application.ports.UserRepository;
import com.example.book_store_back.identity.domain.UserId;

public class GetCurrentUserInteractor implements GetCurrentUserUseCase {

    private final UserRepository userRepository;

    public GetCurrentUserInteractor(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResult execute(String uid) {
        UserId id = new UserId(uid);

        UserResult userResult = userRepository.findById(id).map(u -> {
            return new UserResult(
                    u.getUserId().value(),
                    u.getEmail().value(),
                    u.getFullName().value(),
                    u.getUserRole().name(),
                    u.getUserStatus().name(),
                    u.getProfilePictureUrl().value());
        }).orElseThrow(() -> new RuntimeException("Usuario no encontrado con el UID: " + uid));

        return userResult;
    }

}
