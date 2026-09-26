package com.example.book_store_back.orders.application.ports;

import com.example.book_store_back.orders.domain.ShippingAddress;
import com.example.book_store_back.orders.domain.ShippingCost;

public interface ShippingPricingService {
    ShippingCost calculateFor(ShippingAddress address);
}