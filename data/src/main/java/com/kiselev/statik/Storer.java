package com.kiselev.statik;

import com.google.common.collect.Lists;
import com.kiselev.statik.model.Event;
import com.kiselev.statik.model.EventType;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class Storer {

    private final Map<EventType, List<Event>> storage = new HashMap<>();

    public void store(EventType type, Event event) {
        // TODO: Implement real storing
        fakeStore(type, event);
    }

    private void fakeStore(EventType type, Event event) {
        if (storage.containsKey(type)) {
            storage.get(type).add(event);
        } else {
            storage.put(type, Lists.newArrayList(event));
        }
    }
}
