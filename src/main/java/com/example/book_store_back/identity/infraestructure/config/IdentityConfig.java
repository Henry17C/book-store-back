package com.example.book_store_back.identity.infraestructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.book_store_back.identity.application.ports.UserRepository;
import com.example.book_store_back.identity.application.usecases.GetCurrentUserInteractor;
import com.example.book_store_back.identity.application.usecases.GetCurrentUserUseCase;
import com.example.book_store_back.identity.application.usecases.SyncFirebaseUserInteractor;
import com.example.book_store_back.identity.application.usecases.SyncFirebaseUserUseCase;

@Configuration 
public class IdentityConfig {

    @Bean
    public SyncFirebaseUserUseCase syncFirebaseUserUseCase(UserRepository userRepository){

        return new SyncFirebaseUserInteractor(userRepository);
    }

    @Bean 
    public GetCurrentUserUseCase getCurrentUserUseCase(UserRepository userRepository){
        return new GetCurrentUserInteractor(userRepository);
    }
}
