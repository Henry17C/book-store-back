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
    
    // 1
    @Id
    private UUID id;

    // 2
    @Column(nullable = false)
    private String title;

    // 3
    @Column(name = "isbn", nullable = false, unique = true)
    private String isbnValue;

    // 4
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Language language;

    // 5
    @Column(name = "price_amount", nullable = false)
    private BigDecimal priceAmount;

    // 6
    @Column(name = "price_currency", nullable = false, length = 3)
    private String priceCurrency;

    // 7
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookFormat format;

    // 8
    @Column(name = "release_date", nullable = false)
    private LocalDateTime releaseDate;

    // 9
    @Column(name = "synopsis", columnDefinition = "TEXT")
    private String sypnosis;

    // 10
    @Column(name = "cover_url")
    private String coverUrl;

    // 11
    @Column(name = "is_recommended", nullable = false)
    private Boolean isRecomended;

    // 12
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookStatus status;

    // 13
    @ElementCollection(fetch = FetchType.EAGER) 
    @CollectionTable(
        name = "book_authors", 
        joinColumns = @JoinColumn(name = "book_id")
    )
    @Column(name = "author_id")
    private List<UUID> authorIds;

    // 14
    @Column(name = "has_stock", nullable = false)
    private Boolean hasStock;

    // 15
    @Column(name = "average_rating")
    private Double averageRating;

    // 16
    @Column(name = "total_reviews")
    private Integer totalReview;
}