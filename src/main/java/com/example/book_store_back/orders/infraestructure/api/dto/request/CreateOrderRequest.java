package com.example.book_store_back.orders.infraestructure.api.dto.request;

import java.util.List;
import java.util.stream.Collectors;

import com.example.book_store_back.orders.application.dtos.CreateOrderCommand;
import com.example.book_store_back.orders.application.dtos.CreateOrderCommand.AddressCommand;
import com.example.book_store_back.orders.application.dtos.CreateOrderCommand.OrderItemCommand;

public record CreateOrderRequest(
                AddressRequest shippingAddress,
                List<OrderItemRequest> items

) {

        public record AddressRequest(
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

        public record OrderItemRequest(
                        String bookId,
                        int quantity) {

        }

        public CreateOrderCommand toCommand(String safeCustomerId) {
                AddressCommand addressCommand = null;
                if (this.shippingAddress != null) {
                        addressCommand = new AddressCommand(
                                        this.shippingAddress.province(),
                                        this.shippingAddress.city(),
                                        this.shippingAddress.sector(),
                                        this.shippingAddress.place(),
                                        this.shippingAddress.phone(),
                                        this.shippingAddress.recipient(),
                                        this.shippingAddress.mainStreet(),
                                        this.shippingAddress.number(),
                                        this.shippingAddress.crossStreet(),
                                        this.shippingAddress.neighborhood(),
                                        this.shippingAddress.reference(),
                                        this.shippingAddress.zipCode(),
                                        this.shippingAddress.country());
                }

                List<OrderItemCommand> items = this.items.stream().map((orderItemRequest) -> {
                        return new OrderItemCommand(orderItemRequest.bookId(), orderItemRequest.quantity());
                }).collect(Collectors.toList());

                return new CreateOrderCommand(
                                safeCustomerId,
                                addressCommand,
                                items);
        }
}
