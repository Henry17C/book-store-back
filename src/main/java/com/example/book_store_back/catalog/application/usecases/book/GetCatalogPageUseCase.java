package com.example.book_store_back.catalog.application.usecases.book;

import com.example.book_store_back.catalog.application.dtos.book.PageResult;
import com.example.book_store_back.catalog.application.dtos.book.CatalogBookResponse;
public interface GetCatalogPageUseCase {
    PageResult<CatalogBookResponse> execute(int page, int size);
}
