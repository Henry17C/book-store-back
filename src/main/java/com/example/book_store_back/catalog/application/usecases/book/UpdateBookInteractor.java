package com.example.book_store_back.catalog.application.usecases.book;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.example.book_store_back.catalog.application.dtos.book.UpdateBookCommand;
import com.example.book_store_back.catalog.application.ports.AuthorRepository;
import com.example.book_store_back.catalog.application.ports.BookRepository;
import com.example.book_store_back.catalog.domain.Book;
import com.example.book_store_back.catalog.domain.BookDescription;
import com.example.book_store_back.catalog.domain.BookFormat;
import com.example.book_store_back.catalog.domain.Isbn;
import com.example.book_store_back.catalog.domain.Language;
import com.example.book_store_back.catalog.domain.Money;

public class UpdateBookInteractor implements UpdateBookUseCase {

    private final BookRepository bookRepository;

    private final AuthorRepository authorRepository;

    public UpdateBookInteractor(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public void execute(UUID bookId, UpdateBookCommand command) {

        // 1. Buscar el libro
        Book book = bookRepository.findById(bookId).orElseThrow(() -> {
            throw new RuntimeException("El libro con el id " + bookId + "no existe.");
        });

        // 2. Validar que los nuevos autores existan
        List<UUID> newAuthorIds = command.authorIds();
        if (newAuthorIds != null && !newAuthorIds.isEmpty()) {
            long existingAuthorsCount = authorRepository.countByIds(newAuthorIds);
            if (existingAuthorsCount != newAuthorIds.size()) {
                throw new IllegalArgumentException("Uno o más autores proporcionados no existen.");
            }
        }

        // 3. Preparar los Value Objects con los datos actualizados
        String newTitle = command.title();
        Isbn newIsbn = new Isbn(command.isbn());
        Money newPrice = new Money(command.price().amount(), command.price().currency());
        BookFormat newFormat = BookFormat.fromString(command.format());
        Language newLanguage = Language.valueOf(command.languaje().toUpperCase());
        LocalDateTime newReleaseDate = command.releaseDate();
        BookDescription newBookDescription = new BookDescription(command.description());

        // 4. Modificar el estado del Agregado (usando sus métodos de dominio)
        book.updateAuthors(newAuthorIds);
        book.updateInformation(newTitle, newIsbn, newLanguage, newPrice, newFormat, newReleaseDate, newBookDescription);

        // 5. Guardar los cambios
        bookRepository.save(book);
    }

}
