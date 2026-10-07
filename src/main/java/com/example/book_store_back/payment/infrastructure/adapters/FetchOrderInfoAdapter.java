package com.example.book_store_back.payment.infrastructure.adapters;
import org.springframework.stereotype.Component;

import com.example.book_store_back.orders.application.api.OrderInternalApi;
import com.example.book_store_back.orders.application.api.OrderTotalDTO;
import com.example.book_store_back.payment.application.ports.FetchOrderInfo;
import com.example.book_store_back.payment.domain.Money;
import com.example.book_store_back.payment.domain.OrderId;

@Component
public class FetchOrderInfoAdapter implements FetchOrderInfo {

    private final OrderInternalApi orderInternalApi;

    public FetchOrderInfoAdapter(OrderInternalApi orderInternalApi) {
        this.orderInternalApi = orderInternalApi;
    }

    @Override
    public Money getOrderTotal(OrderId orderId) {
        // 1. Llamada en memoria al módulo de Order
        OrderTotalDTO dto = orderInternalApi.getOrderTotal(orderId.value().toString());

        // 2. Mapeo del DTO "anémico" al Value Object rico del dominio de Pagos
        return new Money(dto.amount(), dto.currency());
    }
}