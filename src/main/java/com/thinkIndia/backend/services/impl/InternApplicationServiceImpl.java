package com.thinkIndia.backend.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thinkIndia.backend.entities.InternApplication;
import com.thinkIndia.backend.entities.User;
import com.thinkIndia.backend.repositories.InternApplicationRepo;
import com.thinkIndia.backend.services.InternApplicationService;

@Service
public class InternApplicationServiceImpl implements InternApplicationService{

    @Autowired
    private InternApplicationRepo internApplicationRepository;
    @Override
    public InternApplication saveApplication(InternApplication application) {
        return internApplicationRepository.save(application);
    }
    @Override
    public List<InternApplication> getAppliedInternships(User user) {
        return internApplicationRepository.findByUser(user);
    }

}
