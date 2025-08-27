package com.thinkIndia.backend.services;

import java.util.List;

import com.thinkIndia.backend.entities.Glimpses;

public interface GlimpsesService {
    public List<Glimpses> findAll();
    public Glimpses createEvent(Glimpses event);
    public void deleteByName(String eventName);
    public Glimpses findByName(String name);
}
