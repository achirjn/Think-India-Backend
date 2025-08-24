package com.thinkIndia.backend.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.thinkIndia.backend.entities.Glimpses;
import com.thinkIndia.backend.repositories.GlimpsesRepo;
import com.thinkIndia.backend.services.GlimpsesService;

import jakarta.transaction.Transactional;

@Service
public class GlimpsesServiceImpl implements GlimpsesService{

    @Autowired
    private GlimpsesRepo eventRepository;

    @Override
    public List<Glimpses> findAll() {
        return eventRepository.findAll(Sort.by(Sort.Direction.DESC, "date"));
    }

    @Override
    public Glimpses createEvent(Glimpses event) {
        return eventRepository.save(event);
    }

    @Override
    @Transactional
    public void deleteByName(String eventName){
        eventRepository.deleteByName(eventName);
    }
}
