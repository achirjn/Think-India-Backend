package com.thinkIndia.backend.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thinkIndia.backend.entities.Internship;
import com.thinkIndia.backend.repositories.InternshipRepo;
import com.thinkIndia.backend.services.InternshipService;

@Service
public class InternshipServiceImpl implements InternshipService{

    @Autowired
    private InternshipRepo internshipRepository;
    @Override
    public Optional<Internship> findById(int id) {
        return internshipRepository.findById(id);
    }
    @Override
    public List<Internship> getUpcommingInternships() {
        return internshipRepository.findUpcommingInternships();
    }
    @Override
    public Internship saveInternship(Internship internship) {
        return internshipRepository.save(internship);
    }
    @Override
    public void deleteById(int id) {
        internshipRepository.deleteById(id);
    }

}
