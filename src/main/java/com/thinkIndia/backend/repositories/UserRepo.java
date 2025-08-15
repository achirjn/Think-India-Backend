package com.thinkIndia.backend.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.thinkIndia.backend.entities.User;

@Repository
public interface UserRepo extends JpaRepository<User, Integer>{

    public Optional<User> findByEmail(String email);

}
