package com.thinkIndia.backend.services;

import java.util.List;
import java.util.Optional;

import com.thinkIndia.backend.entities.Recommendations;

public interface RecommendService {

    public Recommendations saveRecommendation(Recommendations recommendation);
    public List<Recommendations> showUnresolvedRecommendations();
    public List<Recommendations> showResolvedRecommendations();
    public void deleteById(int id);
    public Optional<Recommendations> getById(int id);
}
