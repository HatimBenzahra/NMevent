package com.example.nmevents;

import java.util.ArrayList;
import java.util.List;

public class EventManager {
    private static EventManager instance;
    private List<Event> eventList;

    private EventManager() {
        eventList = new ArrayList<>();
    }

    public static EventManager getInstance() {
        if (instance == null) {
            instance = new EventManager();
        }
        return instance;
    }

    public void addEvent(Event event) {
        eventList.add(event);
    }

    public List<Event> getEventList() {
        return eventList;
    }
}
