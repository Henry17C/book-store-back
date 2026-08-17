package com.example.book_store_back.catalog.application.strategies;

import com.example.book_store_back.catalog.application.dtos.book.CatalogBookResult;
import com.example.book_store_back.catalog.application.dtos.book.PageResult;
import com.example.book_store_back.catalog.application.ports.BookQueryGateway;

public class RecommendedStrategy implements CategoryStrategy {
    private final BookQueryGateway gateway;

    public RecommendedStrategy(BookQueryGateway gateway){
        this.gateway=gateway;
    }

    @Override
    public boolean supports(CategoryCode categoryCode) {
        return CategoryCode.RECOMMENDED.equals(categoryCode);
    }

    @Override
    public PageResult<CatalogBookResult> fetch(int page, int size, Boolean onlyInStock) {
        return gateway.findRecommended(page, size, onlyInStock);
    }

}
