package com.example.book_store_back.catalog.application.usecases.book;

import java.util.UUID;

import com.example.book_store_back.catalog.application.ports.BookRepository;
import com.example.book_store_back.catalog.domain.Book;

public class ArchiveBookInteractor implements ArchiveBookUseCase {

    private final BookRepository bookRepository;

    public ArchiveBookInteractor(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public void execute(UUID id) {

        // 1. Buscar el libro
        Book book = bookRepository.findById(id).orElseThrow(() -> {
            throw new RuntimeException("Libro no encontrado.");
        });

        // 2. Archivar (Intención de negocio)
        book.archive();

        // 3. Guardar el estado actualizado

        bookRepository.save(book);

    }

}
