package com.thinkIndia.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.thinkIndia.backend.dto.UserDto;
import com.thinkIndia.backend.entities.User;
import com.thinkIndia.backend.services.UserService;


@Controller
@RequestMapping("/auth")
public class UserAuthController {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired 
    private UserService userService;

    @CrossOrigin(origins = {"http://localhost:5173"})
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(UserDto userDto) {
        String encodedPassword = passwordEncoder.encode(userDto.getPassword());
        User user = new User(userDto.getName(), userDto.getEmail(), encodedPassword);
        userService.savUser(user);
        return new ResponseEntity<>("Registration Successfull.", HttpStatus.OK);
    }
    
}
