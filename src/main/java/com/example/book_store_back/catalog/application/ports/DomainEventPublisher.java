package com.example.book_store_back.catalog.application.ports;

public interface DomainEventPublisher {
    void publish(Object event);
}
