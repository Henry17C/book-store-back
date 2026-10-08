package com.example.book_store_back.catalog.infrastructure.integration;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.example.book_store_back.catalog.application.api.BookSnapshotDTO;
import com.example.book_store_back.catalog.application.api.CatalogInternalApi;
import com.example.book_store_back.catalog.application.ports.BookRepository;
import com.example.book_store_back.catalog.domain.Book;

@Component 
public class CatalogInternalApiImpl implements CatalogInternalApi {

    private final BookRepository bookRepository;

    public CatalogInternalApiImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<BookSnapshotDTO> getBooksSnapshots(List<UUID> bookIds) {
        // Buscar todos los libros solicitados de una sola vez
        List<Book> books = bookRepository.findAllByIds(bookIds);

        // Mapear la entidad completa a un DTO anémico para el exterior
        return books.stream()
                .map(book -> new BookSnapshotDTO(
                        book.getId(),
                        book.getPrice().amount(),
                        book.getPrice().currency(),
                        book.getFormat().asString() // Enum a String
                ))
                .collect(Collectors.toList());
    }
}