package com.example.book_store_back.catalog.application.ports;

import com.example.book_store_back.catalog.application.dtos.book.CatalogBookResponse;
import com.example.book_store_back.catalog.application.dtos.book.PageResult;

public interface BookQueryGateway {
    PageResult<CatalogBookResponse> getCatalogPage(int page, int size);

    PageResult<CatalogBookResponse> findNewReleases(int page, int size, Boolean onlyInStock); //strategy
    PageResult<CatalogBookResponse> findRecommended(int page, int size, Boolean onlyInStock); //strategy
    PageResult<CatalogBookResponse> findBestSellers(int page, int size, Boolean onlyInStock); //strategy

}
