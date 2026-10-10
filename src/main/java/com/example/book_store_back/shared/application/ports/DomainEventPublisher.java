package com.example.book_store_back.shared.application.ports;

public interface DomainEventPublisher {
    public void publish(Object event);
}