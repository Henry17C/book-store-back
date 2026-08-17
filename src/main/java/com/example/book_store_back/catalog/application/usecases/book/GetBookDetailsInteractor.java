package com.example.book_store_back.catalog.application.usecases.book;

import java.util.List;
import java.util.UUID;

import com.example.book_store_back.catalog.application.dtos.book.BookDetailsResult;
import com.example.book_store_back.catalog.application.dtos.book.MoneyResul;
import com.example.book_store_back.catalog.application.ports.AuthorRepository;
import com.example.book_store_back.catalog.application.ports.BookRepository;
import com.example.book_store_back.catalog.application.ports.ReviewRepository;
import com.example.book_store_back.catalog.domain.Author;
import com.example.book_store_back.catalog.domain.Book;
import com.example.book_store_back.catalog.domain.Review;

public class GetBookDetailsInteractor implements GetBookDetailsUseCase {

    public final BookRepository bookRepository;
    public final AuthorRepository authorRepository;
    public final ReviewRepository reviewRepository;

    public GetBookDetailsInteractor(BookRepository bookRepository, AuthorRepository authorRepository,
            ReviewRepository reviewRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.reviewRepository = reviewRepository;
    }

    @Override
    public BookDetailsResult execute(UUID bookId) {

        // 1. Buscar el libro
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("Libro no encontrado."));
        // 2.Buscar los autores (Gateway)
        List<Author> authors = authorRepository.findAllByIds(book.getAuthorIds());

        // 3. Buscar las reviews (Gateway)
        List<Review> reviews = reviewRepository.findByBookId(book.getId());

        // 4. Mapear
        MoneyResul price = new MoneyResul(book.getPrice().amount(), book.getPrice().currency());

        List<String> authorNames = authors.stream().map(a -> {
            return a.getName();
        }).toList();

        Double averageRating = reviews.stream()
                .mapToDouble((review) -> {
                    return review.getRating();
                })
                .reduce(0.0, (a, b) -> {
                    return a + b;
                }) / (reviews.isEmpty() ? 1.0 : reviews.size());

        return new BookDetailsResult(book.getId(), book.getTitle(), book.getIsbn().value(),
                book.getFormat().value().name(), authorNames, averageRating, book.getBookDescription().value(), "URL",
                price, false);
        // return null;
    }

}
