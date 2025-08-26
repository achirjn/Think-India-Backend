package com.thinkIndia.backend.services;

import java.util.List;
import java.util.Optional;

import com.thinkIndia.backend.entities.Events;

public interface EventsService {

    public Events saveEvent(Events event);
    public Optional<Events> findById(int id);
    public List<Events> getPastEvents();
    public List<Events> getUpcommingEvents();
    public List<Events> getHiddenEvents();
}
