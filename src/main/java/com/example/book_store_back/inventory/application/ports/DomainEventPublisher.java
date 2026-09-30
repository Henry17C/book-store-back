package com.example.book_store_back.inventory.application.ports;

public interface DomainEventPublisher {
    public void publish (Object event);
}
