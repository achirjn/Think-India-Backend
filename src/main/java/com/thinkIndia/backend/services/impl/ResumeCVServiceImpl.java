package com.thinkIndia.backend.services.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thinkIndia.backend.entities.ResumeCV;
import com.thinkIndia.backend.repositories.ResumeCVRepo;
import com.thinkIndia.backend.services.ResumeCVService;

@Service
public class ResumeCVServiceImpl implements ResumeCVService{

    @Autowired
    private ResumeCVRepo resumeCVRepository;
    @Override
    public Optional<ResumeCV> findById(int id) {
        return resumeCVRepository.findById(id);
    }

}
