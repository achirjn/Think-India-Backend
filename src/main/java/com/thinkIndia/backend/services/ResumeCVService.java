package com.thinkIndia.backend.services;

import com.thinkIndia.backend.entities.ResumeCV;
import java.util.Optional;

public interface ResumeCVService {
    public Optional<ResumeCV> findById(int id);
}
