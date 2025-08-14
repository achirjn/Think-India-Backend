package com.thinkIndia.backend.services;

import java.util.List;

import com.thinkIndia.backend.entities.Events;

public interface EventService {
    public List<Events> findAll();
    public Events createEvent(Events event);
    public void deleteByName(String eventName);
}
