package com.thinkIndia.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.thinkIndia.backend.entities.Internship;

import java.util.Optional;


@Repository
public interface InternshipRepo extends JpaRepository<Internship, Integer>{
    public Optional<Internship> findById(int id);
}
