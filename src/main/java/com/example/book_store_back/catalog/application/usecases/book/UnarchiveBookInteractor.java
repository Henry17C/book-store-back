package com.example.book_store_back.catalog.application.usecases.book;

import java.util.UUID;

import com.example.book_store_back.catalog.application.ports.BookRepository;
import com.example.book_store_back.catalog.domain.Book;

public class UnarchiveBookInteractor implements UnarchiveBookUseCase {

    private final BookRepository bookRepository;

    public UnarchiveBookInteractor(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public void execute(UUID bookId) {
        // 1. Buscar el libro
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("Libro no encontrado con el ID: " + bookId));
        // 2. Desarchivar
        book.unarchive();

        // 3. Guardar el libro con su nuevo estado
        bookRepository.save(book);

    }

}
