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
    private String coverUrl;

    private Boolean hasStock;
    // Desnormalización controlada.
    private Double averageRating;
    private Integer totalReviews;

    public Book(UUID id, String title, Isbn isbn, Language language, Money price, BookFormat format,
            LocalDateTime releaseDate, BookDescription bookDescription, String coverUrl, Boolean isRecommended,
            BookStatus status, List<UUID> authorIds, Boolean hasStock, Double averageRating, Integer totalReviews) {
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
        this.hasStock = Objects.requireNonNull(hasStock, "El estado de stock no puede ser nulo.");
        this.coverUrl = coverUrl;
        this.averageRating = averageRating != null ? averageRating : 0.0;
        this.totalReviews = totalReviews != null ? totalReviews : 0;
    }

    public static Book register(UUID id, String title, Isbn isbn, Language language, Money price, BookFormat format,
            LocalDateTime releaseDate, BookDescription bookDescription, String coverUrl, List<UUID> authorIds) {
        return new Book(id, title, isbn, language, price, format, releaseDate, bookDescription, coverUrl, false,
                BookStatus.ACTIVE,
                authorIds, false, 0.0, 0);
    }

    public void updateInformation(String title, Isbn isbn, Language language, Money price, BookFormat format,
            LocalDateTime releaseDate, BookDescription bookDescription, String coverUrl) {

        this.title = Objects.requireNonNull(title, "El titulo no pueder ser nulo.");
        this.isbn = Objects.requireNonNull(isbn, "El ISBN no puede ser nulo.");
        this.price = Objects.requireNonNull(price, "El precio no puede ser nulo.");
        this.format = Objects.requireNonNull(format, "El formato no puede ser nulo.");
        this.language = Objects.requireNonNull(language, "El lenguaje no puede ser nulo.");
        this.releaseDate = Objects.requireNonNull(releaseDate, "La fecha de publicación no puede ser nula.");
        this.bookDescription = Objects.requireNonNull(bookDescription, "La descripción no puede ser nula.");
        this.coverUrl = Objects.requireNonNull(coverUrl, "La portada del libro no puede ser nula.");

    }

    // Acciones de Marketing

    public void markAsRecommended() {
        this.isRecommended = true;
    }

    public void removeRecommendation() {
        this.isRecommended = false;
    }

    // Ciclo de vida (Soft Delete)
    public void archive() {
        if (this.status == BookStatus.ARCHIVED) {
            throw new IllegalStateException("El libro ya se encuentra archivado.");
        }
        this.status = BookStatus.ARCHIVED;
    }

    public void unarchive() {
        this.status = BookStatus.ACTIVE;
    }

    // Método para reemplazar completamente la lista de autores
    public void updateAuthors(List<UUID> newAuthorIds) {
        if (newAuthorIds == null || newAuthorIds.isEmpty()) {
            throw new IllegalArgumentException("El libro debe tener al menos un autor.");
        }
        // Creamos una nueva lista para asegurar la mutabilidad y desenlazar la
        // referencia anterior
        this.authorIds = new ArrayList<>(newAuthorIds);
    }

    // Modificar Authores del libro
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
    public void changeCover(String newCoverUrl) {
        if (newCoverUrl == null || newCoverUrl.trim().isEmpty()) {
            throw new IllegalArgumentException("La URL de la portada no puede estar vacía.");
        }
        this.coverUrl = newCoverUrl;
    }

    // =========================================
    // Sincronización de Inventario (Consistencia Eventual)
    // =========================================

    public void markAsInStock() {
        this.hasStock = true;
    }

    public void markAsOutOfStock() {
        this.hasStock = false;
    }

    public Boolean hasStock() {
        return this.hasStock;
    }

    public void addNewReview(Integer newRating) {
        if (newRating == null || newRating < 1 || newRating > 5) {
            throw new IllegalArgumentException("El rating debe estar entre 1 y 5.");
        }

        // Fórmula del promedio móvil:
        // Nuevo Promedio = ((Promedio Actual * Total Reseñas) + Nuevo Rating) / (Total
        // Reseñas + 1)

        double currentTotalScore = this.averageRating * this.totalReviews;
        this.totalReviews += 1;

        double newAverage = (currentTotalScore + newRating) / this.totalReviews;
        this.averageRating = Math.round(newAverage * 10.0) / 10.0;
    }

    public void updateReview(Integer previousRating, Integer newRating) {
        if (newRating == null || newRating < 1 || newRating > 5) {
            throw new IllegalArgumentException("El rating debe estar entre 1 y 5.");
        }

        if (previousRating == null || previousRating < 1 || previousRating > 5) {
            throw new IllegalArgumentException("El rating debe estar entre 1 y 5.");
        }

        // Fórmula del promedio móvil:
        // Nuevo Promedio = ((Promedio Actual * Total Reseñas) + Nuevo Rating - Rating
        // Previo ) / (Total)

        double currentTotalScore = this.averageRating * this.totalReviews;

        double newAverage = (currentTotalScore - previousRating + newRating) / this.totalReviews;
        this.averageRating = Math.round(newAverage * 10.0) / 10.0;
    }

    public void removeReviewRating(Integer previousRating) {
        if (previousRating == null || previousRating < 1 || previousRating > 5) {
            throw new IllegalArgumentException("El rating debe estar entre 1 y 5.");
        }

        if (this.totalReviews <= 1) {
            this.totalReviews = 0;
            this.averageRating = 0.0;
            return;
        }
        // Fórmula del promedio móvil:
        // Nuevo Promedio = ((Promedio Actual * Total Reseñas -1 )- Rating Previo ) /
        // (Total)
        double currentTotalScore = this.averageRating * this.totalReviews;
        this.totalReviews -= 1;

        double newAverage = (currentTotalScore - previousRating) / this.totalReviews;
        this.averageRating = Math.round(newAverage * 10.0) / 10.0;
    }

    // Getters
    public List<UUID> getAuthorIds() {
        return List.copyOf(authorIds);
    }

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

    public BookDescription getBookDescription() {
        return this.bookDescription;
    }

    public Language getLanguage() {
        return this.language;
    }

    public Double getAverageRating() {
        return this.averageRating;
    }

    public Integer getTotalReviews() {
        return this.totalReviews;
    }

    public Boolean getHasStock() {
        return this.hasStock;
    }

    public String getCoverUrl(){
        return this.coverUrl;
    }
}
