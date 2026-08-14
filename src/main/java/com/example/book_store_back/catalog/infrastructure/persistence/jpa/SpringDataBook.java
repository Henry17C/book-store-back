package com.example.book_store_back.catalog.infrastructure.persistence.jpa;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.book_store_back.catalog.infrastructure.persistence.entities.BookEntity;
@Repository
public interface SpringDataBook extends JpaRepository<BookEntity, UUID>{
    boolean existsByIsbnValue(String isbnValue);

    @Query("SELECT b FROM BookEntity b JOIN b.authorIds a WHERE a = :authorId")
    public List<BookEntity> findBooksByAuthorId(@Param("authorId") UUID authorId);

}
