package com.example.book_store_back.identity.infraestructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.book_store_back.identity.application.ports.UserRepository;
import com.example.book_store_back.identity.application.usecases.SyncFirebaseUserInteractor;
import com.example.book_store_back.identity.application.usecases.SyncFirebaseUserUseCase;

@Configuration 
public class IdentityConfig {

    @Bean
    public SyncFirebaseUserUseCase SyncFirebaseUserUseCase(UserRepository userRepository){

        return new SyncFirebaseUserInteractor(userRepository);
    }
}
