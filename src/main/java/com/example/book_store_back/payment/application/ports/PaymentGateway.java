package com.example.book_store_back.payment.application.ports;

import com.example.book_store_back.payment.application.dtos.PaymentIntentResult;
import com.example.book_store_back.payment.domain.Money;
import com.example.book_store_back.payment.domain.OrderId;

public interface PaymentGateway {
    PaymentIntentResult createPaymentIntent(OrderId orderId, Money amount);
}
