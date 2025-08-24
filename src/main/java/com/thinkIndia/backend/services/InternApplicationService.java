package com.thinkIndia.backend.services;

import java.util.List;

import com.thinkIndia.backend.entities.InternApplication;
import com.thinkIndia.backend.entities.User;

public interface InternApplicationService {
    public InternApplication saveApplication(InternApplication application);
    public List<InternApplication> getAppliedInternships(User user);
}
