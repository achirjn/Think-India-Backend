package com.thinkIndia.backend.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thinkIndia.backend.entities.EventRegistration;
import com.thinkIndia.backend.entities.User;
import com.thinkIndia.backend.repositories.EventRegistrationRepo;
import com.thinkIndia.backend.services.EventRegistrationService;

@Service
public class EventRegistrationServiceImpl implements EventRegistrationService{

    @Autowired
    private EventRegistrationRepo eventRegistrationRepository;
    @Override
    public EventRegistration saveRegistration(EventRegistration registration) {
        return eventRegistrationRepository.save(registration);
    }
    @Override
    public List<EventRegistration> getRegisteredEvents(User user) {
        return eventRegistrationRepository.findByUser(user);
    }

}
