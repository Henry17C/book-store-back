package com.example.book_store_back.identity.application.usecases;

import java.util.Optional;

import com.example.book_store_back.identity.application.dtos.SyncFirebaseUserCommand;
import com.example.book_store_back.identity.application.dtos.UserResult;
import com.example.book_store_back.identity.application.ports.UserRepository;
import com.example.book_store_back.identity.domain.Email;
import com.example.book_store_back.identity.domain.FullName;
import com.example.book_store_back.identity.domain.ProfilePictureUrl;
import com.example.book_store_back.identity.domain.User;
import com.example.book_store_back.identity.domain.UserId;

public class SyncFirebaseUserInteractor implements SyncFirebaseUserUseCase {
    private final UserRepository userRepository;

    public SyncFirebaseUserInteractor(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResult execute(SyncFirebaseUserCommand command) {

        UserId id = new UserId(command.firebaseUid());
        Email email = new Email(command.email());
        FullName fullName = new FullName(command.fullName());
        ProfilePictureUrl picture = new ProfilePictureUrl(command.pictureUrl());

        Optional<User> existingUser = userRepository.findById(id);

        User user = existingUser.orElseGet(() -> {
            User newUser = User.registerWithGoogle(id, email, fullName, picture);
            userRepository.save(newUser);
            return newUser;
        });

        return new UserResult(
                user.getUserId().value(),
                user.getEmail().value(),
                user.getFullName().value(),
                user.getProfilePictureUrl() != null ? user.getProfilePictureUrl().value() : null,
                user.getUserRole().name(),
                user.getUserStatus().name());
    }
}
