package com.example.book_store_back.catalog.application.ports;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.example.book_store_back.catalog.domain.Author;

public interface AuthorRepository {
    public Optional<Author> findById(UUID id);
    public List<Author> findByName(String name) ;
    public List<Author> findAllByIds(List<UUID> ids);
    public  List<Author> findAll ();
    public void save(Author author);


}
