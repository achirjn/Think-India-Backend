package com.thinkIndia.backend.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thinkIndia.backend.entities.Recommendations;
import com.thinkIndia.backend.repositories.RecommendRepo;
import com.thinkIndia.backend.services.RecommendService;

@Service
public class RecommendServiceImpl implements RecommendService{
    @Autowired
    private RecommendRepo recommendRepo;

    @Override
    public Recommendations saveRecommendation(Recommendations recommendation) {
        return recommendRepo.save(recommendation);
    }

    @Override
    public List<Recommendations> showUnresolvedRecommendations() {
        return recommendRepo.findUnresolvedRecommendations();
    }    
    @Override
    public List<Recommendations> showResolvedRecommendations() {
        return recommendRepo.findResolvedRecommendations();
    }

    @Override
    public void deleteById(int id) {
        recommendRepo.deleteById(id);
    }

    @Override
    public Optional<Recommendations> getById(int id) {
        return recommendRepo.findById(id);
    }    
}
