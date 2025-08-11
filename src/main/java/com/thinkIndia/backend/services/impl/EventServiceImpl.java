package com.thinkIndia.backend.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.thinkIndia.backend.entities.Events;
import com.thinkIndia.backend.repositories.EventRepo;
import com.thinkIndia.backend.services.EventService;

@Service
public class EventServiceImpl implements EventService{

    @Autowired
    private EventRepo eventRepository;

    @Override
    public List<Events> findAll() {
        return eventRepository.findAll(Sort.by(Sort.Direction.DESC, "date"));
    }

    @Override
    public Events createEvent(Events event) {
        return eventRepository.save(event);
    }

}
