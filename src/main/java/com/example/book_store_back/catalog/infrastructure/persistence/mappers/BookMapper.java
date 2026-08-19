package com.example.book_store_back.catalog.infrastructure.persistence.mappers;


import org.springframework.stereotype.Component;

import com.example.book_store_back.catalog.domain.Book;
import com.example.book_store_back.catalog.domain.BookDescription;
import com.example.book_store_back.catalog.domain.BookFormat;
import com.example.book_store_back.catalog.domain.Isbn;
import com.example.book_store_back.catalog.domain.Money;
import com.example.book_store_back.catalog.infrastructure.persistence.entities.BookEntity;

@Component
public class BookMapper {
    public Book toDomain(BookEntity entity) {
        if (entity == null) {
            return null;
        }

        Money money = new Money(entity.getPriceAmount(), entity.getPriceCurrency());
        BookDescription description = new BookDescription(entity.getSypnosis());
        Isbn isbn = new Isbn(entity.getIsbnValue());
        BookFormat format= new BookFormat(entity.getFormat());
        return new Book(entity.getId(), entity.getTitle(), isbn, entity.getLanguage(), money,
                format, entity.getReleaseDate(), description, entity.getCoverUrl(),
                entity.getIsRecommended(), entity.getStatus(), entity.getAuthorIds(), entity.getHasStock(),
                entity.getAverageRating(), entity.getTotalReviews());

    }

    public BookEntity toEntity(Book book) {
        if (book == null) {
            return null;
        }
        return new BookEntity(
                book.getId(), // 1. id
                book.getTitle(), // 2. title
                book.getIsbn().value(), // 3. isbnValue
                book.getLanguage(), // 4. language
                book.getPrice().amount(), // 5. priceAmount
                book.getPrice().currency(), // 6. priceCurrency
                book.getFormat().value(), // 7. format
                book.getReleaseDate(), // 8. releaseDate
                book.getBookDescription().value(), // 9. sypnosis
                book.getCoverUrl(), // 10. coverUrl
                book.isRecommended(), // 11. isRecomended
                book.getStatus(), // 12. status
                book.getAuthorIds(), // 13. authorIds
                book.hasStock(), // 14. hasStock
                book.getAverageRating(), // 15. averageRating
                book.getTotalReviews() // 16. totalReview
        );
    }
}
