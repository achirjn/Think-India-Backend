package com.thinkIndia.backend.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.thinkIndia.backend.entities.EventRegistration;
import com.thinkIndia.backend.entities.compositeKeys.EventRegistrationKey;
import com.thinkIndia.backend.entities.User;


@Repository
public interface EventRegistrationRepo extends JpaRepository<EventRegistration, EventRegistrationKey>{
    
    public List<EventRegistration> findByUser(User user);
}
