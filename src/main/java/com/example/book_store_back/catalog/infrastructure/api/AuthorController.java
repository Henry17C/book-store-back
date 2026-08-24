package com.example.book_store_back.catalog.infrastructure.api;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.book_store_back.catalog.application.dtos.author.SearchAuthorQuery;
import com.example.book_store_back.catalog.application.usecases.author.RegisterAuthorUseCase;
import com.example.book_store_back.catalog.application.usecases.author.SearchAuthorsByNameUseCase;
import com.example.book_store_back.catalog.application.usecases.author.UpdateAuthorUseCase;
import com.example.book_store_back.catalog.infrastructure.api.dto.request.author.RegisterAuthorRequest;
import com.example.book_store_back.catalog.infrastructure.api.dto.request.author.UpdateAuthorRequest;
import com.example.book_store_back.catalog.infrastructure.api.dto.response.author.AuthorDetailsResponse;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    private final RegisterAuthorUseCase registerAuthorUseCase;
    private final SearchAuthorsByNameUseCase searchAuthorsByNameUseCase;
    private final UpdateAuthorUseCase updateAuthorUseCase;

    public AuthorController(RegisterAuthorUseCase registerAuthorUseCase,
            SearchAuthorsByNameUseCase searchAuthorsByNameUseCase,
            UpdateAuthorUseCase updateAuthorUseCase) {
        this.registerAuthorUseCase = registerAuthorUseCase;
        this.searchAuthorsByNameUseCase = searchAuthorsByNameUseCase;
        this.updateAuthorUseCase = updateAuthorUseCase;

    }

    @PostMapping
    public ResponseEntity<Void> registerAuthor(@RequestBody RegisterAuthorRequest request) {
        registerAuthorUseCase.execute(request.toCommand());
        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    @GetMapping
    public ResponseEntity<List<AuthorDetailsResponse>> searchAuthorsByName(
            @RequestParam 
            @NotBlank(message = "El término de búsqueda no puede estar vacío.")
            @Size(min = 2, message = "Ingresa al menos 2 letras para buscar.") 
            String name) {
                
        SearchAuthorQuery query = new SearchAuthorQuery(name);

        List<AuthorDetailsResponse> webResponse = searchAuthorsByNameUseCase.execute(query).stream().map(result -> {
            return new AuthorDetailsResponse(result.id(), result.name(), result.biography());
        }).toList();

        return ResponseEntity.ok(webResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateAuthor(@PathVariable UUID id, @RequestBody UpdateAuthorRequest request) {
        updateAuthorUseCase.execute(id, request.toCommand());
        return ResponseEntity.noContent().build();
    }

}
