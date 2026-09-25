package com.example.book_store_back.catalog.application.usecases.book;

import java.util.List;
import java.util.UUID;

import org.springframework.transaction.annotation.Transactional;

import com.example.book_store_back.catalog.application.dtos.book.BookSnapshotResult;
import com.example.book_store_back.catalog.application.ports.BookRepository;
import com.example.book_store_back.catalog.domain.Book;

@Transactional(readOnly = true)
public class GetBooksSnapshotInteractor implements GetBooksSnapshotUseCase {
    private final BookRepository bookRepository;

    public GetBooksSnapshotInteractor(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<BookSnapshotResult> execute(List<UUID> bookIds) {

        List<Book> books = bookRepository.findAllByIds(bookIds);

        return books.stream()
                .map(book -> new BookSnapshotResult(
                        book.getId(),
                        book.getPrice().amount(),
                        book.getPrice().currency(),
                        book.getFormat().asString()))
                .toList();
    }
}