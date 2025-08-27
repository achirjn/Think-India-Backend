package com.thinkIndia.backend.services;

import java.util.List;
import java.util.Optional;

import com.thinkIndia.backend.entities.InternPlacements;

public interface InternPlacementsService {
    public List<InternPlacements> getInternPlacements();
    public InternPlacements saveInternPlacedData(InternPlacements internPlacement);
    public void deleteById(int id);
    public Optional<InternPlacements> findById(int id);
}
