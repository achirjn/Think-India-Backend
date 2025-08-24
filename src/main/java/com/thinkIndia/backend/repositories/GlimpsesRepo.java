package com.thinkIndia.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.thinkIndia.backend.entities.Glimpses;

@Repository
public interface GlimpsesRepo extends JpaRepository<Glimpses, Integer>{
    
    public void deleteByName(String eventName);
}
