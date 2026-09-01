package com.example.book_store_back.catalog.application.usecases.book;

import java.util.List;

import com.example.book_store_back.catalog.application.dtos.book.BookDetailsResult;
import com.example.book_store_back.catalog.application.dtos.book.MoneyResul;
import com.example.book_store_back.catalog.application.ports.AuthorRepository;
import com.example.book_store_back.catalog.application.ports.BookRepository;
import com.example.book_store_back.catalog.application.ports.ReviewRepository;
import com.example.book_store_back.catalog.domain.Book;
import com.example.book_store_back.catalog.domain.Review;

public class GetBookDetailsByIsbnInteractor implements GetBookDetailsByIsbnUseCase {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final ReviewRepository reviewRepository;
    
    public GetBookDetailsByIsbnInteractor(BookRepository bookRepository,final AuthorRepository authorRepository, ReviewRepository reviewRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository=authorRepository;
        this.reviewRepository=reviewRepository;
    }

    public BookDetailsResult execute(String isbn) {
        
        Book book = bookRepository.findByIsbn(isbn);

        List<String> authorNames= authorRepository.findAllByIds(book.getAuthorIds()).stream().map(a->{
            return a.getName();
        }).toList();
        
        List<Review> reviews = reviewRepository.findByBookId(book.getId());

        MoneyResul moneyResul = new MoneyResul(book.getPrice().amount(), book.getPrice().currency());
        
        Double averageRating = reviews.stream()
                .mapToDouble((review) -> {
                    return review.getRating();
                })
                .reduce(0.0, (a, b) -> {
                    return a + b;
                }) / (reviews.isEmpty() ? 1.0 : reviews.size());
        
        return new BookDetailsResult(
                book.getId(),
                book.getTitle(),
                book.getIsbn().value(),
                book.getFormat().value().name(),
                authorNames,
                averageRating,
                book.getBookDescription().value(),
                book.getCoverUrl(),
                moneyResul,
                book.getHasStock());
    }

}
