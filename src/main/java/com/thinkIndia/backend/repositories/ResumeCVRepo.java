package com.thinkIndia.backend.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.thinkIndia.backend.entities.ResumeCV;

@Repository
public interface ResumeCVRepo extends JpaRepository<ResumeCV, Integer>{

    public Optional<ResumeCV> findById(int id);
}
