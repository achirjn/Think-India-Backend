package com.thinkIndia.backend.services;

import java.util.Optional;

import com.thinkIndia.backend.entities.Internship;

public interface InternshipService {
    public Optional<Internship> findById(int id);
}
