package com.example.book_store_back.catalog.infrastructure.persistence.adapters;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.example.book_store_back.catalog.application.ports.BookRepository;
import com.example.book_store_back.catalog.domain.Book;
import com.example.book_store_back.catalog.infrastructure.persistence.entities.BookEntity;
import com.example.book_store_back.catalog.infrastructure.persistence.jpa.SpringDataBook;
import com.example.book_store_back.catalog.infrastructure.persistence.mappers.BookMapper;
@Component
public class BookRepositoryAdapter implements BookRepository {

    private final BookMapper mapper;
    private final SpringDataBook jpaRepository;

    public BookRepositoryAdapter(BookMapper mapper, SpringDataBook jpaRepository) {
        this.mapper = mapper;
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Optional<Book> findById(UUID id) {

        return jpaRepository.findById(id).map(entity -> mapper.toDomain(entity));
    }

    @Override
    public List<Book> searchByAuthor(UUID authorId) {
        return jpaRepository.findBooksByAuthorId(authorId).stream().map(mapper::toDomain).toList();
    }

    @Override
    public void save(Book book) {
        BookEntity entity = mapper.toEntity(book);

        jpaRepository.save(entity);
    }

    @Override
    public boolean existsByIsbn(String isbn) {

        return jpaRepository.existsByIsbnValue(isbn);
    }

    @Override
    public Boolean existsById(UUID id) {

        return jpaRepository.existsById(id);
    }

}
