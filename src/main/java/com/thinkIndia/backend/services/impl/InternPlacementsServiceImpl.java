package com.thinkIndia.backend.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thinkIndia.backend.entities.InternPlacements;
import com.thinkIndia.backend.repositories.InternPlacementsRepo;
import com.thinkIndia.backend.services.InternPlacementsService;

@Service
public class InternPlacementsServiceImpl implements InternPlacementsService{

    @Autowired
    private InternPlacementsRepo internPlacementsRepository;
    
    @Override
    public List<InternPlacements> getInternPlacements() {
        return internPlacementsRepository.findAll();
    }

    @Override
    public InternPlacements saveInternPlacedData(InternPlacements internPlacement) {
        return internPlacementsRepository.save(internPlacement);
    }

    @Override
    public void deleteById(int id) {
        internPlacementsRepository.deleteById(id);
    }

    @Override
    public Optional<InternPlacements> findById(int id) {
        return internPlacementsRepository.findById(id);
    }

}
