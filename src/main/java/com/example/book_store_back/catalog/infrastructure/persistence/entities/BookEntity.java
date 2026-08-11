package com.example.book_store_back.catalog.infrastructure.persistence.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.example.book_store_back.catalog.domain.BookFormat;
import com.example.book_store_back.catalog.domain.BookStatus;
import com.example.book_store_back.catalog.domain.Language;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "books")
@AllArgsConstructor @NoArgsConstructor @Getter @Setter
public class BookEntity {
    @Id
    private UUID id;

    @Column(nullable = false)
    private String title;

    @Column(name = "isbn", nullable = false, unique = true)
    private String isbnValue;

    @Column(name = "price_amount", nullable = false)
    private BigDecimal priceAmount;

    @Column(name = "price_currency", nullable = false, length = 3)
    private String priceCurrency;

    @Column(name = "synopsis", columnDefinition = "TEXT")
    private String sypnosis;

    @Column(name = "cover_url")
    private String coverUrl;

    @Column(name = "release_date", nullable = false)
    private LocalDateTime releaseDate;

    @Column(name = "is_recommended", nullable = false)
    private Boolean isRecomended;

    @Column(name = "has_stock", nullable = false)
    private Boolean hasStock;

    @Column(name = "average_rating")
    private Double averageRating;

    @Column(name = "total_reviews")
    private Integer totalReview;

    @ElementCollection(fetch = FetchType.EAGER) 
    @CollectionTable(
        name = "book_authors", 
        joinColumns = @JoinColumn(name = "book_id")
    )
    @Column(name = "author_id")
    private List<UUID> authorIds;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookFormat format;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Language language;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookStatus status;
}
