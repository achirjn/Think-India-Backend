package com.thinkIndia.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.thinkIndia.backend.entities.InternApplication;
import com.thinkIndia.backend.entities.compositeKeys.InternApplicationKey;

import java.util.List;

import com.thinkIndia.backend.entities.User;


@Repository
public interface InternApplicationRepo extends JpaRepository<InternApplication, InternApplicationKey>{

    public List<InternApplication> findByUser(User user);
}
