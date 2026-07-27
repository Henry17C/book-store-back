package com.example.book_store_back.catalog.application.strategies;

import com.example.book_store_back.catalog.application.dtos.book.CatalogBookResponse;
import com.example.book_store_back.catalog.application.dtos.book.PageResult;
import com.example.book_store_back.catalog.application.ports.BookQueryGateway;

public class NewReleasesStrategy implements CategoryStrategy {
    private final BookQueryGateway gateway;

    public NewReleasesStrategy(BookQueryGateway gateway) {
        this.gateway = gateway;
    }

    @Override
    public boolean supports(CategoryCode categoryCode) {
        return CategoryCode.NEW_RELEASES.equals(categoryCode);
    }

    @Override
    public PageResult<CatalogBookResponse> fetch(int page, int size, Boolean onlyInStock) {
        return gateway.findNewReleases(page, size, onlyInStock);
    }
}
