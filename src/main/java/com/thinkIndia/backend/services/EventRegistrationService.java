package com.thinkIndia.backend.services;

import java.util.List;

import com.thinkIndia.backend.entities.EventRegistration;
import com.thinkIndia.backend.entities.User;

public interface EventRegistrationService {

    public EventRegistration saveRegistration(EventRegistration registration);

    public List<EventRegistration> getRegisteredEvents(User user);
}
