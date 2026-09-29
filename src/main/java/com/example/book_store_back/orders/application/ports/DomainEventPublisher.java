package com.example.book_store_back.orders.application.ports;

public interface DomainEventPublisher {
    public void publish (Object event);
}
