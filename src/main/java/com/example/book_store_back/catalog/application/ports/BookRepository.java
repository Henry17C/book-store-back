package com.example.book_store_back.catalog.application.ports;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.example.book_store_back.catalog.domain.Book;

public interface BookRepository {
    public Optional<Book> findById(UUID id);
    public List<Book> searchByAuthor(UUID authorId);
    public void save(Book book);
    boolean existsByIsbn(String isbn);
    public Optional<Book> findById();
    public Boolean existsById(UUID id);
    
}
