package com.thinkIndia.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.thinkIndia.backend.entities.Events;

@Repository
public interface EventRepo extends JpaRepository<Events, Integer>{
    
    public void deleteByName(String eventName);
}
