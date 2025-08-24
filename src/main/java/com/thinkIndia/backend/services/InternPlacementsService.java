package com.thinkIndia.backend.services;

import java.util.List;

import com.thinkIndia.backend.entities.InternPlacements;

public interface InternPlacementsService {
    public List<InternPlacements> getInternPlacements();
    public InternPlacements saveInternPlacedData(InternPlacements internPlacement);
    public void deleteById(int id);
}
