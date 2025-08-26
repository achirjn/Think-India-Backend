package com.thinkIndia.backend.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thinkIndia.backend.entities.Events;
import com.thinkIndia.backend.repositories.EventsRepo;
import com.thinkIndia.backend.services.EventsService;

@Service
public class EventsServiceImpl implements EventsService{

    @Autowired
    private EventsRepo eventsRepository;
    @Override
    public Events saveEvent(Events event) {
        return eventsRepository.save(event);
    }
    @Override
    public Optional<Events> findById(int id) {
        return eventsRepository.findById(id);
    }
    @Override
    public List<Events> getPastEvents() {
        return eventsRepository.getEvents(0);
    }
    @Override
    public List<Events> getUpcommingEvents() {
        return eventsRepository.getEvents(1);
    }
    @Override
    public List<Events> getHiddenEvents() {
        return eventsRepository.getHiddenEvents();
    }

}
