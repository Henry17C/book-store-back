package com.example.book_store_back.identity.infraestructure.persistence.adapters;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.example.book_store_back.identity.application.ports.UserRepository;
import com.example.book_store_back.identity.domain.Email;
import com.example.book_store_back.identity.domain.User;
import com.example.book_store_back.identity.domain.UserId;
import com.example.book_store_back.identity.infraestructure.persistence.entities.UserEntity;
import com.example.book_store_back.identity.infraestructure.persistence.jpa.StringUserRepository;
import com.example.book_store_back.identity.infraestructure.persistence.mappers.UserMapper;

@Repository 
public class UserRepositoryAdapter implements UserRepository {

    private final StringUserRepository jpaRepository;
    private final UserMapper mapper;

    public UserRepositoryAdapter(StringUserRepository jpaRepository, UserMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;

    }

    @Override
    public void save(User user) {

        UserEntity entity = mapper.toEntity(user);
        jpaRepository.save(entity);
    }

    @Override
    public Optional<User> findById(UserId id) {
        String rawId = id.value();
        return jpaRepository.findById(rawId).map(mapper::toDomain);
    }

    @Override
    public Optional<User> findByEmail(Email email) {
        String rawEmail = email.value();
        return jpaRepository.findByEmail(rawEmail).map(mapper::toDomain);
    }

}
