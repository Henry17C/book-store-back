package com.example.book_store_back.catalog.infrastructure.persistence.adapters;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.example.book_store_back.catalog.application.ports.AuthorRepository;
import com.example.book_store_back.catalog.domain.Author;
import com.example.book_store_back.catalog.infrastructure.persistence.entities.AuthorEntity;
import com.example.book_store_back.catalog.infrastructure.persistence.jpa.SpringDataAuthorRepository;
import com.example.book_store_back.catalog.infrastructure.persistence.mappers.AuthorMapper;

@Component
public class AuthorRepositoryAdapter  implements  AuthorRepository{
    private final SpringDataAuthorRepository jpaRepository;
    private final AuthorMapper mapper;

    public AuthorRepositoryAdapter(SpringDataAuthorRepository jpaRepository, AuthorMapper mapper){
        this.jpaRepository=jpaRepository;
        this.mapper=mapper;
    }
    
    @Override
    public Optional<Author> findById(UUID id) {

        
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Author> findByName(String name) {

        return jpaRepository.findByName(name).stream().map(mapper::toDomain).toList();
    }

    @Override
    public List<Author> findAllByIds(List<UUID> ids) {

        return jpaRepository.findAllById(ids).stream().map(mapper::toDomain).toList();
    }

    @Override
    public List<Author> findAll() {
        return jpaRepository.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public void save(Author author) {
        AuthorEntity authorEntity = mapper.toEntity(author);
        jpaRepository.save(authorEntity);
    }

    @Override
    public long countByIds(List<UUID> authorIds) {
        return jpaRepository.countByIdIn(authorIds);
    }
    
}
