package com.example.book_store_back.catalog.application.ports;

import com.example.book_store_back.catalog.application.dtos.book.CatalogBookResponse;
import com.example.book_store_back.catalog.application.dtos.book.PageResult;

public interface BookQueryGateway {
    PageResult<CatalogBookResponse> getCatalogPage(int page, int size);
}
