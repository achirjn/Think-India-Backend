package com.thinkIndia.backend.services;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.thinkIndia.backend.entities.User;

public interface UserService extends UserDetailsService{

    public User savUser(User user);

}
