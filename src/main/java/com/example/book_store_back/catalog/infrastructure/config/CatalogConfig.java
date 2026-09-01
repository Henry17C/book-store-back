package com.example.book_store_back.catalog.infrastructure.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.book_store_back.catalog.application.listeners.UpdateBookRatingOnReviewArchivedListener;
import com.example.book_store_back.catalog.application.listeners.UpdateBookRatingOnReviewCreatedListener;
import com.example.book_store_back.catalog.application.listeners.UpdateBookRatingOnReviewUpdatedListener;
import com.example.book_store_back.catalog.application.ports.AuthorRepository;
import com.example.book_store_back.catalog.application.ports.BookQueryGateway;
import com.example.book_store_back.catalog.application.ports.BookRepository;
import com.example.book_store_back.catalog.application.ports.DomainEventPublisher;
import com.example.book_store_back.catalog.application.ports.InventoryGateway;
import com.example.book_store_back.catalog.application.ports.ReviewRepository;
import com.example.book_store_back.catalog.application.strategies.BestSellersStrategy;
import com.example.book_store_back.catalog.application.strategies.CategoryStrategy;
import com.example.book_store_back.catalog.application.strategies.NewReleasesStrategy;
import com.example.book_store_back.catalog.application.strategies.RecommendedStrategy;
import com.example.book_store_back.catalog.application.usecases.author.RegisterAuthorInteractor;
import com.example.book_store_back.catalog.application.usecases.author.RegisterAuthorUseCase;
import com.example.book_store_back.catalog.application.usecases.author.SearchAuthorsByNameInteractor;
import com.example.book_store_back.catalog.application.usecases.author.SearchAuthorsByNameUseCase;
import com.example.book_store_back.catalog.application.usecases.author.UpdateAuthorInteractor;
import com.example.book_store_back.catalog.application.usecases.author.UpdateAuthorUseCase;
import com.example.book_store_back.catalog.application.usecases.book.ArchiveBookInteractor;
import com.example.book_store_back.catalog.application.usecases.book.ArchiveBookUseCase;
import com.example.book_store_back.catalog.application.usecases.book.GetBookDetailsByIsbnInteractor;
import com.example.book_store_back.catalog.application.usecases.book.GetBookDetailsByIsbnUseCase;
import com.example.book_store_back.catalog.application.usecases.book.GetBookDetailsInteractor;
import com.example.book_store_back.catalog.application.usecases.book.GetBookDetailsUseCase;
import com.example.book_store_back.catalog.application.usecases.book.GetCatalogPageInteractor;
import com.example.book_store_back.catalog.application.usecases.book.GetCatalogPageUseCase;
import com.example.book_store_back.catalog.application.usecases.book.GetCategorizedBooksInteractor;
import com.example.book_store_back.catalog.application.usecases.book.GetCategorizedBooksUseCase;
import com.example.book_store_back.catalog.application.usecases.book.RecommendBookInteractor;
import com.example.book_store_back.catalog.application.usecases.book.RecommendBookUseCase;
import com.example.book_store_back.catalog.application.usecases.book.RegisterBookInteractor;
import com.example.book_store_back.catalog.application.usecases.book.RegisterBookUseCase;
import com.example.book_store_back.catalog.application.usecases.book.RemoveBookRecommendationInteractor;
import com.example.book_store_back.catalog.application.usecases.book.RemoveBookRecommendationUseCase;
import com.example.book_store_back.catalog.application.usecases.book.SearchBooksInteractor;
import com.example.book_store_back.catalog.application.usecases.book.SearchBooksUseCase;
import com.example.book_store_back.catalog.application.usecases.book.UnarchiveBookInteractor;
import com.example.book_store_back.catalog.application.usecases.book.UnarchiveBookUseCase;
import com.example.book_store_back.catalog.application.usecases.book.UpdateBookInteractor;
import com.example.book_store_back.catalog.application.usecases.book.UpdateBookUseCase;
import com.example.book_store_back.catalog.application.usecases.review.ArchiveReviewInteractor;
import com.example.book_store_back.catalog.application.usecases.review.ArchiveReviewUseCase;
import com.example.book_store_back.catalog.application.usecases.review.UpdateReviewInteractor;
import com.example.book_store_back.catalog.application.usecases.review.UpdateReviewUseCase;
import com.example.book_store_back.catalog.application.usecases.review.WriteReviewInteractor;
import com.example.book_store_back.catalog.application.usecases.review.WriteReviewUseCase;

@Configuration
public class CatalogConfig {

    // ==========================================================
    // BEANS DE CASOS DE USO (USE CASES / INTERACTORS)
    // ==========================================================

    // ****** AUTHOR ******
    @Bean
    public RegisterAuthorUseCase registerAuthorUseCase(AuthorRepository authorRepository) {
        return new RegisterAuthorInteractor(authorRepository);
    }

    @Bean
    public SearchAuthorsByNameUseCase searchAuthorsByNameUseCase(AuthorRepository authorRepository){
        return new SearchAuthorsByNameInteractor(authorRepository);
    }

    @Bean
    public UpdateAuthorUseCase updateAuthorUseCase(AuthorRepository authorRepository){
        return new UpdateAuthorInteractor(authorRepository);
    }

    // ***** BOOK *****
    @Bean
    public ArchiveBookUseCase archiveBookUseCase(BookRepository bookRepository) {
        return new ArchiveBookInteractor(bookRepository);
    }

    @Bean
    public GetBookDetailsUseCase getBookDetailsUseCase(BookRepository bookRepository,
            AuthorRepository authorRepository,
            ReviewRepository reviewRepository) {
        return new GetBookDetailsInteractor(bookRepository, authorRepository, reviewRepository);
    }

    @Bean
    public GetCatalogPageUseCase getCatalogPageUseCase(BookQueryGateway bookQueryGateway, InventoryGateway inventoryGateway) {
        return new GetCatalogPageInteractor(bookQueryGateway, inventoryGateway);
    }

    @Bean
    public GetCategorizedBooksUseCase getCategorizedBooksUseCase(BookQueryGateway bookQueryGateway) {
        
        // 1. Instanciar las estrategias manualmente
        List<CategoryStrategy> strategies = List.of(
                new BestSellersStrategy(bookQueryGateway)
                // A medida que se creen más, agregar aquí
                , new NewReleasesStrategy(bookQueryGateway)
                , new RecommendedStrategy(bookQueryGateway)
        );

        // 2. Lista llena al Interactor
        return new GetCategorizedBooksInteractor(strategies);
    }

    @Bean
    public RecommendBookUseCase recommendBookUseCase(BookRepository bookRepository) {
        return new RecommendBookInteractor(bookRepository);
    }

    @Bean
    public RegisterBookUseCase registerBookUseCase(BookRepository bookRepository, AuthorRepository authorRepository) {
        return new RegisterBookInteractor(bookRepository, authorRepository); // <-- Falta de ';' corregida
    }

    @Bean
    public RemoveBookRecommendationUseCase removeBookRecommendationUseCase(BookRepository bookRepository) {
        return new RemoveBookRecommendationInteractor(bookRepository);
    }

    @Bean
    public SearchBooksUseCase searchBooksUseCase(BookQueryGateway bookQueryGateway) {
        return new SearchBooksInteractor(bookQueryGateway);
    }

    @Bean
    public UnarchiveBookUseCase unarchiveBookUseCase(BookRepository bookRepository) {
        return new UnarchiveBookInteractor(bookRepository);
    }

    @Bean
    public UpdateBookUseCase updateBookUseCase(BookRepository bookRepository, AuthorRepository authorRepository) {
        return new UpdateBookInteractor(bookRepository, authorRepository);
    }

    @Bean
    public GetBookDetailsByIsbnUseCase getBookDetailsByIsbnUseCase (BookRepository bookRepository,
            AuthorRepository authorRepository,
            ReviewRepository reviewRepository) {
        return new GetBookDetailsByIsbnInteractor(bookRepository, authorRepository, reviewRepository);
    }

    // ***** REVIEW *****
    @Bean
    public ArchiveReviewUseCase archiveReviewUseCase(ReviewRepository reviewRepository, DomainEventPublisher publisher) {
        return new ArchiveReviewInteractor(reviewRepository, publisher);
    }

    @Bean
    public UpdateReviewUseCase updateReviewUseCase(ReviewRepository reviewRepository, DomainEventPublisher domainEventPublisher) {
        return new UpdateReviewInteractor(reviewRepository, domainEventPublisher); // <-- Falta de ';' corregida
    }

    @Bean
    public WriteReviewUseCase writeReviewUseCase(BookRepository bookRepository, ReviewRepository reviewRepository, DomainEventPublisher eventPublisher) {
        return new WriteReviewInteractor(bookRepository, reviewRepository, eventPublisher);
    }

    // ==========================================================
    // BEANS DE LISTENERS DE APLICACIÓN
    // ==========================================================

    @Bean
    public UpdateBookRatingOnReviewArchivedListener updateBookRatingOnReviewArchivedListener(BookRepository bookRepository) {
        return new UpdateBookRatingOnReviewArchivedListener(bookRepository);
    }

    @Bean
    public UpdateBookRatingOnReviewCreatedListener updateBookRatingOnReviewCreatedListener(BookRepository bookRepository) {
        return new UpdateBookRatingOnReviewCreatedListener(bookRepository);
    }

    @Bean
    public UpdateBookRatingOnReviewUpdatedListener updateBookRatingOnReviewUpdatedListener(BookRepository bookRepository, ReviewRepository reviewRepository) {
        return new UpdateBookRatingOnReviewUpdatedListener(bookRepository, reviewRepository);
    }
}