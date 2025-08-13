package com.thinkIndia.backend.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.thinkIndia.backend.entities.User;
import com.thinkIndia.backend.repositories.UserRepo;
import com.thinkIndia.backend.services.UserService;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepo userRepository;

    @Override
    public User loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
    if (user == null) {
        throw new UsernameNotFoundException("User not found with email: " + email);
    }
    return user;
    }

    @Override
    public User savUser(User user) {
        return userRepository.save(user);
    }

}
