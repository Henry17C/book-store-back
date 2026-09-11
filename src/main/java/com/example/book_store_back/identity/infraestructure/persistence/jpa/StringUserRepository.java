package com.example.book_store_back.identity.infraestructure.persistence.jpa;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.book_store_back.identity.infraestructure.persistence.entities.UserEntity;

public interface StringUserRepository extends  JpaRepository<UserEntity, String>{
    
    public Optional<UserEntity> findByEmail(String email);
}
