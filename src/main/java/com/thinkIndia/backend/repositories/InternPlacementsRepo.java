package com.thinkIndia.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.thinkIndia.backend.entities.InternPlacements;

@Repository
public interface InternPlacementsRepo extends JpaRepository<InternPlacements, Integer>{

}
