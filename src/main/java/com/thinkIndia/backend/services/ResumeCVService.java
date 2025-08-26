package com.thinkIndia.backend.services;

import java.util.Optional;

import com.thinkIndia.backend.entities.ResumeCV;

public interface ResumeCVService {
    public Optional<ResumeCV> findById(int id);

    public void deleteById(int resumeId);
}
