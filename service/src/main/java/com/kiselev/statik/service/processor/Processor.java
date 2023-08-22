package com.kiselev.statik.service.processor;

import com.kiselev.statik.Storer;
import com.kiselev.statik.model.Event;
import com.kiselev.statik.model.EventType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Processor {

    private final Storer storer;

    public String process(Event event) {
        // Recognition
        EventType type = recognize(event);

        // Storing
        storer.store(type, event);

        return String.format("Event {id: %s, title: %s} successfully processed.",
                event.getId(),
                event.getMessage());
    }

    private EventType recognize(Event event) {
        return EventType.TIMESTAMP;
    }
}
