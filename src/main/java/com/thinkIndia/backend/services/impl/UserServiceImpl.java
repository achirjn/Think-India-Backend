package com.thinkIndia.backend.services.impl;

import java.util.Optional;

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
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
        return userOptional.get();
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

}
