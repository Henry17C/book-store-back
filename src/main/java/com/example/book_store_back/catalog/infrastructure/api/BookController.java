package com.example.book_store_back.catalog.infrastructure.api;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.book_store_back.catalog.application.dtos.book.BookDetailsResult;
import com.example.book_store_back.catalog.application.dtos.book.CatalogBookResult;
import com.example.book_store_back.catalog.application.dtos.book.CategorizedBooksQuery;
import com.example.book_store_back.catalog.application.dtos.book.PageResult;
import com.example.book_store_back.catalog.application.dtos.book.SearchBooksQuery;
import com.example.book_store_back.catalog.application.strategies.CategoryCode;
import com.example.book_store_back.catalog.application.usecases.book.ArchiveBookUseCase;
import com.example.book_store_back.catalog.application.usecases.book.GetBookDetailsByIsbnUseCase;
import com.example.book_store_back.catalog.application.usecases.book.GetBookDetailsUseCase;
import com.example.book_store_back.catalog.application.usecases.book.GetCatalogPageUseCase;
import com.example.book_store_back.catalog.application.usecases.book.GetCategorizedBooksUseCase;
import com.example.book_store_back.catalog.application.usecases.book.RecommendBookUseCase;
import com.example.book_store_back.catalog.application.usecases.book.RegisterBookUseCase;
import com.example.book_store_back.catalog.application.usecases.book.RemoveBookRecommendationUseCase;
import com.example.book_store_back.catalog.application.usecases.book.SearchBooksUseCase;
import com.example.book_store_back.catalog.application.usecases.book.UnarchiveBookUseCase;
import com.example.book_store_back.catalog.application.usecases.book.UpdateBookUseCase;
import com.example.book_store_back.catalog.infrastructure.api.dto.request.book.RegisterBookRequest;
import com.example.book_store_back.catalog.infrastructure.api.dto.request.book.UpdateBookRequest;
import com.example.book_store_back.catalog.infrastructure.api.dto.response.book.BookDetailsResponse;
import com.example.book_store_back.catalog.infrastructure.api.dto.response.book.CatalogBookResponse;
import com.example.book_store_back.catalog.infrastructure.api.dto.response.book.MoneyResponse;
import com.example.book_store_back.catalog.infrastructure.api.dto.response.book.PageResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/books")
public class BookController {

    private final ArchiveBookUseCase archiveBookUseCase;
    private final GetBookDetailsUseCase getBookDetailsUseCase;
    private final GetCatalogPageUseCase getCatalogPageUseCase;
    private final GetCategorizedBooksUseCase getCategorizedBooksUseCase;
    private final RecommendBookUseCase recommendBookUseCase;
    private final RegisterBookUseCase registerBookUseCase;
    private final RemoveBookRecommendationUseCase removeBookRecommendationUseCase;
    private final SearchBooksUseCase searchBooksUseCase;
    private final UnarchiveBookUseCase unarchiveBookUseCase;
    private final UpdateBookUseCase updateBookUseCase;
    private final GetBookDetailsByIsbnUseCase getBookDetailsByIsbn;

    public BookController(ArchiveBookUseCase archiveBookUseCase,
            GetBookDetailsUseCase getBookDetailsUseCase,
            GetCatalogPageUseCase getCatalogPageUseCase,
            GetCategorizedBooksUseCase getCategorizedBooksUseCase,
            RecommendBookUseCase recommendBookUseCase,
            RegisterBookUseCase registerBookUseCase,
            RemoveBookRecommendationUseCase removeBookRecommendationUseCase,
            SearchBooksUseCase searchBooksUseCase,
            UnarchiveBookUseCase unarchiveBookUseCase,
            UpdateBookUseCase updateBookUseCase,
            GetBookDetailsByIsbnUseCase getBookDetailsByIsbn) {

        this.archiveBookUseCase = archiveBookUseCase;
        this.getBookDetailsUseCase = getBookDetailsUseCase;
        this.getCatalogPageUseCase = getCatalogPageUseCase;
        this.getCategorizedBooksUseCase = getCategorizedBooksUseCase;
        this.recommendBookUseCase = recommendBookUseCase;
        this.registerBookUseCase = registerBookUseCase;
        this.removeBookRecommendationUseCase = removeBookRecommendationUseCase;
        this.searchBooksUseCase = searchBooksUseCase;
        this.unarchiveBookUseCase = unarchiveBookUseCase;
        this.updateBookUseCase = updateBookUseCase;
        this.getBookDetailsByIsbn = getBookDetailsByIsbn;
    }

    // OBTENER DETALLES DE UN LIBRO

    // Ruta: GET /books/isbn/{isbn}
    @GetMapping("/isbn/{isbn}")
    public ResponseEntity<BookDetailsResponse> getBookDetailsByIsbn(@PathVariable String isbn) {
        BookDetailsResult bookresult = getBookDetailsByIsbn.execute(isbn);
        MoneyResponse moneyResponse = new MoneyResponse(bookresult.price().amount(), bookresult.price().currency());

        BookDetailsResponse bookDetailsResponse = new BookDetailsResponse(
                bookresult.id(), bookresult.title(), bookresult.isnb(), bookresult.format(),
                bookresult.authorNames(), bookresult.averageRating(), bookresult.bookDescription(),
                bookresult.bookCoverUrl(), moneyResponse, bookresult.inStock());

        return ResponseEntity.ok(bookDetailsResponse);
    }

    // Ruta: GET /books/{id}
    @GetMapping("/{id}")
    public ResponseEntity<BookDetailsResponse> getBookDetails(@PathVariable UUID id) {
        BookDetailsResult bookDetailsResult = getBookDetailsUseCase.execute(id);

        BookDetailsResponse bookDetailsResponse = new BookDetailsResponse(
                bookDetailsResult.id(),
                bookDetailsResult.title(),
                bookDetailsResult.isnb(),
                bookDetailsResult.format(),
                bookDetailsResult.authorNames(),
                bookDetailsResult.averageRating(),
                bookDetailsResult.bookDescription(),
                bookDetailsResult.bookCoverUrl(),
                new MoneyResponse(bookDetailsResult.price().amount(), bookDetailsResult.price().currency()),
                bookDetailsResult.inStock());
        return ResponseEntity.ok(bookDetailsResponse);
    }

    // OBTENER EL CATALOGO PAGINADO
    // Ruta: GET /books?page=0&size=10
    @GetMapping()
    public ResponseEntity<PageResponse<CatalogBookResponse>> getCatalogPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        PageResult<CatalogBookResult> pageResult = getCatalogPageUseCase.execute(page, size);

        List<CatalogBookResponse> content = pageResult.content().stream().map(c -> {
            return new CatalogBookResponse(c.id(), c.title(), c.coverUrl(), c.authorNames(),
                    new MoneyResponse(c.price().amount(), c.price().currency()), c.averageRating(), c.inStock());
        }).toList();

        int currentPage = pageResult.currentPage();
        int totalPages = pageResult.totalPages();
        long totalElements = pageResult.totalElements();
        PageResponse<CatalogBookResponse> pageResponse = new PageResponse<>(content, currentPage,
                totalPages, totalElements);

        return ResponseEntity.ok(pageResponse);
    }

    // OBTENER LIBROS POR CATEGORÍA
    // Ruta: GET /books/category/{code}?page=0&size=10
    @GetMapping("category/{code}")
    public ResponseEntity<PageResponse<CatalogBookResponse>> getCategorizedBooks(
            @PathVariable String code,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "false") Boolean onlyInStock) {
        CategoryCode codeEnum = CategoryCode.valueOf(code);
        PageResult<CatalogBookResult> pageResult = getCategorizedBooksUseCase
                .execute(new CategorizedBooksQuery(codeEnum, page, size, onlyInStock));

        List<CatalogBookResponse> content = pageResult.content().stream().map(c -> {
            return new CatalogBookResponse(c.id(), c.title(), c.coverUrl(), c.authorNames(),
                    new MoneyResponse(c.price().amount(), c.price().currency()), c.averageRating(), c.inStock());
        }).toList();
        int currentPage = pageResult.currentPage();
        int totalPages = pageResult.totalPages();
        long totalElements = pageResult.totalElements();

        PageResponse<CatalogBookResponse> pageResponse = new PageResponse<>(content, currentPage, totalPages,
                totalElements);

        return ResponseEntity.ok(pageResponse);
    }

    // REGISTRAR UN LIBRO (POST)
    @PostMapping
    public ResponseEntity<Void> registerBook(@Valid @RequestBody RegisterBookRequest request) {
        UUID newBookId = registerBookUseCase.execute(request.toCommand());

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newBookId)
                .toUri();

        return ResponseEntity.created(location).build();
    }

    // QUITAR RECOMENDACIÓN DE UN LIBRO
    // Ruta: DELETE /books/{id}/recommend
    @DeleteMapping("/{id}/recommend")
    public ResponseEntity<Void> removeBookRecommendation(@PathVariable UUID id) {
        removeBookRecommendationUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }

    // BUSCAR LIBROS (GET)
    // Ruta: GET /books/search?keyword=java&page=0&size=10
    @GetMapping("search")
    public ResponseEntity<PageResponse<CatalogBookResponse>> searchBooks(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "0") int size,
            @RequestParam(defaultValue = "false") Boolean onlyInStock) {

        PageResult<CatalogBookResult> result = searchBooksUseCase
                .execute(new SearchBooksQuery(keyword, page, size, onlyInStock));

        List<CatalogBookResponse> content = result.content().stream().map(c -> {
            return new CatalogBookResponse(c.id(), c.title(), c.coverUrl(), c.authorNames(),
                    new MoneyResponse(c.price().amount(), c.price().currency()), c.averageRating(), c.inStock());
        }).toList();
        int currentPage = result.currentPage();
        int totalPages = result.totalPages();
        long totalElements = result.totalElements();

        PageResponse<CatalogBookResponse> response = new PageResponse<>(content, currentPage,
                totalPages, totalElements);

        return ResponseEntity.ok(response);

    }

    // =========================================================================
    // ACCIONES ESPECIFICAS (Cambios de estado usando endpoints semánticos)
    // =========================================================================

    // DESARCHIVAR UN LIBRO
    // Ruta: PUT /books/{id}/unarchive
    @PutMapping("/{id}/unarchive")
    public ResponseEntity<Void> unarchiveBook(@PathVariable UUID id) {

        unarchiveBookUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }

    // ACTUALIZAR UN LIBRO
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateBook(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateBookRequest request) {

        updateBookUseCase.execute(id, request.toCommand());
        return ResponseEntity.noContent().build();
    }

    // ARCHIVAR UN LIBRO
    // Ruta: PUT /books/{id}/archive
    @PutMapping("/{id}/archive")
    public ResponseEntity<Void> archiveBook(@PathVariable UUID id) {
        archiveBookUseCase.execute(id);

        return ResponseEntity.noContent().build();
    }

    // RECOMENDAR UN LIBRO
    // Ruta: PUT /books/{id}/recommend
    @PutMapping("/{id}/recommend")
    public ResponseEntity<Void> recommendBook(@PathVariable UUID id) {
        recommendBookUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }

}
