package com.thinkIndia.backend.services;

import java.util.List;
import java.util.Optional;

import com.thinkIndia.backend.entities.Internship;

public interface InternshipService {
    public Optional<Internship> findById(int id);
    public List<Internship> getUpcommingInternships();
    public Internship saveInternship(Internship internship);
    public void deleteById(int id);
}
