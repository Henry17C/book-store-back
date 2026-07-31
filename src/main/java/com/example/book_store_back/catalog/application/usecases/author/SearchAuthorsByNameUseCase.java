package com.example.book_store_back.catalog.application.usecases.author;

import java.util.List;

import com.example.book_store_back.catalog.application.dtos.author.AuthorDetailsResponse;
import com.example.book_store_back.catalog.application.dtos.author.SearchAuthorQuery;

public interface SearchAuthorsByNameUseCase {
    public List<AuthorDetailsResponse> execute(SearchAuthorQuery query);
}
