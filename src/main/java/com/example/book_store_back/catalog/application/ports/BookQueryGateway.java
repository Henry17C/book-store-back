package com.example.book_store_back.catalog.application.ports;

import com.example.book_store_back.catalog.application.dtos.book.CatalogBookResult;
import com.example.book_store_back.catalog.application.dtos.book.PageResult;

public interface BookQueryGateway {
    PageResult<CatalogBookResult> getCatalogPage(int page, int size);

    PageResult<CatalogBookResult> searchByKeyword(String sanitizedKeyword, int page, int size);

    PageResult<CatalogBookResult> findNewReleases(int page, int size, Boolean onlyInStock); // strategy

    PageResult<CatalogBookResult> findRecommended(int page, int size, Boolean onlyInStock); // strategy

    PageResult<CatalogBookResult> findBestSellers(int page, int size, Boolean onlyInStock); // strategy

}
