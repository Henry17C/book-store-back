package com.example.book_store_back.payment.application.ports;

import com.example.book_store_back.payment.domain.Money;
import com.example.book_store_back.payment.domain.OrderId;

public interface FetchOrderInfo {
    Money getOrderTotal(OrderId orderId);
}