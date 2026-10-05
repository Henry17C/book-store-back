package com.example.book_store_back.payment.domain;

import java.util.ArrayList;
import java.util.List;

public abstract class AggregateRoot {
    private final List<Object> domainEvents = new ArrayList<>();

    protected void registerEvent(Object event) {
        this.domainEvents.add(event);
    }

    public List<Object> pullDomainEvents() {
        List<Object> events = new ArrayList<>(this.domainEvents);
        this.domainEvents.clear();
        return events;
    }
}