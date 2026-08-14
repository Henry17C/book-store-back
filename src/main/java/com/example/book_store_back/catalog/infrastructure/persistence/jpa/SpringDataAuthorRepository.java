package com.example.book_store_back.catalog.infrastructure.persistence.jpa;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.book_store_back.catalog.infrastructure.persistence.entities.AuthorEntity;
@Repository
public interface SpringDataAuthorRepository extends JpaRepository<AuthorEntity, UUID> {
    
    public List<AuthorEntity> findByName(String name);

    public long countByIdIn(List<UUID> ids);

    List<AuthorEntity> findByNameContainingIgnoreCase(String name);
}
