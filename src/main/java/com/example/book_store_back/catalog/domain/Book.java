package com.example.book_store_back.catalog.domain;
//Aggregate

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

//Aggregate
public class Book {

    private UUID id;
    private String title;
    private Isbn isbn;
    private Money price;
    private BookFormat format;
    private List<UUID> authorIds;
    private Language language;
    private LocalDateTime releaseDate;
    private Boolean isRecommended;
    private BookStatus status;
    private BookDescription bookDescription;
    // private List<UUID> reviewIds;/// Modificar pues pueden existir infinitas y
    // afecta al redimiento

    public Book(UUID id, String title, Isbn isbn, Language language, Money price, BookFormat format,
            LocalDateTime releaseDate, BookDescription bookDescription, Boolean isRecommended,
            BookStatus status, List<UUID> authorIds, List<UUID> reviewIds) {
        this.id = Objects.requireNonNull(id, "El id del libro no puede ser null.");
        this.title = Objects.requireNonNull(title, "El titulo no pueder ser nulo.");
        this.isbn = Objects.requireNonNull(isbn, "El ISBN no puede ser nulo.");
        this.price = Objects.requireNonNull(price, "El precio no puede ser nulo.");
        this.format = Objects.requireNonNull(format, "El formato no puede ser nulo.");
        this.language = Objects.requireNonNull(language, "El lenguaje no puede ser nulo.");
        this.authorIds = authorIds != null ? new ArrayList<>(authorIds) : new ArrayList<>();
        this.releaseDate = Objects.requireNonNull(releaseDate, "La fecha de publicación no puede ser nula.");
        this.isRecommended = Objects.requireNonNull(isRecommended, "La recomendación no puede ser nula.");
        this.status = Objects.requireNonNull(status, "El estado no puede ser nulo.");
        this.bookDescription = Objects.requireNonNull(bookDescription, "La descripción no puede ser nula.");

    }

    public static Book register(UUID id, String title, Isbn isbn, Language language, Money price, BookFormat format,
            LocalDateTime releaseDate, BookDescription bookDescription) {
        return new Book(id, title, isbn, language, price, format, releaseDate,bookDescription , false, BookStatus.ACTIVE,
                new ArrayList<>(), new ArrayList<>());
    }

    public void setTitle(String title) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("El título del libro no puede estar vacío.");
        }
        this.title = title;

    }

    public void setIsRecommended(Boolean isRecommended) {
        this.isRecommended = isRecommended;
    }

    public void setStatus(BookStatus status) {
        this.status = status;
    }

    public void addAuthor(UUID newIdAuthor) {

        boolean isAdded = authorIds.stream().anyMatch(a -> a.equals(newIdAuthor));
        if (!isAdded) {
            this.authorIds.add(newIdAuthor);
        }
    }

    public void removeAuthor(UUID authorId) {
        if (authorId != null) {
            this.authorIds.removeIf(id -> id.equals(authorId));
        }
    }

    public void setBookDescription(BookDescription bookDescription){
        this.bookDescription= bookDescription;
    }

    public List<UUID> getAuthorIds() {
        return List.copyOf(authorIds);
    }

    // Getters
    public UUID getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public Money getPrice() {
        return this.price;
    }

    public BookFormat getFormat() {
        return this.format;
    }

    public Isbn getIsbn() {
        return this.isbn;
    }

    public LocalDateTime getReleaseDate() {
        return this.releaseDate;
    }

    public Boolean isRecommended() {
        return this.isRecommended;
    }

    public BookStatus getStatus() {
        return this.status;
    }

    public BookDescription getBookDescription(){
        return this.bookDescription;
    }
}
