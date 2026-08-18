package com.example.book_store_back.catalog.infrastructure.events.publisher;


import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import com.example.book_store_back.catalog.application.ports.DomainEventPublisher;

@Component
public class SpringDomainEventPublisher implements DomainEventPublisher{
    
    private final ApplicationEventPublisher springPublisher;

    public SpringDomainEventPublisher(ApplicationEventPublisher springPublisher){
        this.springPublisher=springPublisher;
    }


    @Override
    public void publish(Object event){
        springPublisher.publishEvent(event);

    }


}
