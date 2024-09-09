package com.flab.pokerunner.core;

import com.flab.pokerunner.domain.event.Events;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GateWay {

    private final ApplicationEventPublisher eventPublisher;

    public void publish(Events events) {
        System.out.println("Event: " + events);
        this.eventPublisher.publishEvent(events);
    }
}
