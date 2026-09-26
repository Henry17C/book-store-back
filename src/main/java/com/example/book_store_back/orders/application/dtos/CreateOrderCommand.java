package com.example.book_store_back.orders.application.dtos;

import java.util.List;

public record CreateOrderCommand(
                String customerId,
                AddressCommand shippingAddress, // Nullable si es compra digital
                List<OrderItemCommand> items) {
        public record OrderItemCommand(
                        String bookId,
                        int quantity) {
        }

        public record AddressCommand(
                        String province,
                        String city,
                        String sector,
                        String place,
                        String phone,
                        String recipient,
                        String mainStreet,
                        String number,
                        String crossStreet,
                        String neighborhood,
                        String reference,
                        String zipCode,
                        String country) {
        }
}