package com.example.book_store_back.catalog.infrastructure.persistence.mappers;

import com.example.book_store_back.catalog.domain.Author;
import com.example.book_store_back.catalog.infrastructure.persistence.entities.AuthorEntity;
import org.springframework.stereotype.Component;


@Component
public class AuthorMapper {
    public Author toDomain(AuthorEntity entity){
        if (entity == null) return null;
        return new Author(entity.getId(), entity.getName(), entity.getBiography());
    }

    public AuthorEntity toEntity(Author author){
        if (author == null) return null;
        return new AuthorEntity(author.getId(),author.getName(), author.getBiography());
    }
}
