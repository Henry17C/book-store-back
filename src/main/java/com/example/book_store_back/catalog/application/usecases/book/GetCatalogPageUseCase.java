package com.example.book_store_back.catalog.application.usecases.book;

import com.example.book_store_back.catalog.application.dtos.book.PageResult;
import com.example.book_store_back.catalog.application.dtos.book.CatalogBookResult;
public interface GetCatalogPageUseCase {
    PageResult<CatalogBookResult> execute(int page, int size);
}
