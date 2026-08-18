package com.example.book_store_back.catalog.application.usecases.book;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.example.book_store_back.catalog.application.dtos.book.RegisterBookCommand;
import com.example.book_store_back.catalog.application.ports.AuthorRepository;
import com.example.book_store_back.catalog.application.ports.BookRepository;
import com.example.book_store_back.catalog.domain.Book;
import com.example.book_store_back.catalog.domain.BookDescription;
import com.example.book_store_back.catalog.domain.BookFormat;
import com.example.book_store_back.catalog.domain.Isbn;
import com.example.book_store_back.catalog.domain.Language;
import com.example.book_store_back.catalog.domain.Money;

public class RegisterBookInteractor implements RegisterBookUseCase {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public RegisterBookInteractor(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @Override
    public UUID execute(RegisterBookCommand command) {
        // 1. Validar que el ISBN no exista ya en el sistema
        if (bookRepository.existsByIsbn(command.isbn())) {
            throw new IllegalArgumentException("Ya existe un libro registrado con el ISBN: " + command.isbn());
        }

        List<UUID> authorIds = command.authorIds();
        if (authorIds != null && !authorIds.isEmpty()) {
            // Método del repositorio que cuenta cuántos de esos IDs existen de verdad
            long existingAuthorsCount = authorRepository.countByIds(authorIds);

            if (existingAuthorsCount != authorIds.size()) {
                throw new IllegalArgumentException("Uno o más autores proporcionados no existen en el sistema.");
            }
        }

        // 2. Crear el nuevo Aggregate Root usando tu Factory Method
        UUID id = UUID.randomUUID();
        String title = command.title();
        Isbn isbn = new Isbn(command.isbn());
        Money price = new Money(command.price().amount(), command.price().currency());
        BookFormat format = BookFormat.fromString(command.format());
        Language language = Language.valueOf(command.language().toUpperCase());
        LocalDateTime releaseDate = command.releaseDate();
        BookDescription bookDescription = new BookDescription(command.description());
        String coverUrl= null; //TODO: S3

        // 3. Guardar (INSERT)
        Book book = Book.register(id, title, isbn, language, price, format, releaseDate, bookDescription, coverUrl , authorIds);
        bookRepository.save(book);
        return id;
    }

}
