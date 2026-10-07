package com.example.book_store_back.orders.application.api;

public interface OrderInternalApi {
    public OrderTotalDTO getOrderTotal(String orderId);

}
