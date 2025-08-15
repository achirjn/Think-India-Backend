package com.thinkIndia.backend.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.thinkIndia.backend.dto.UserDto;
import com.thinkIndia.backend.entities.User;
import com.thinkIndia.backend.services.UserService;


@Controller
@RequestMapping("/auth")
public class UserAuthController {
    
    private PasswordEncoder passwordEncoder;
    private UserService userService;

    public UserAuthController(PasswordEncoder passwordEncoder, UserService userService) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }


    // @CrossOrigin(origins = {"http://localhost:5173"})
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(UserDto userDto) {
        String encodedPassword = passwordEncoder.encode(userDto.getPassword());
        User user = new User(userDto.getName(), userDto.getEmail(), encodedPassword);
        userService.saveUser(user);
        return new ResponseEntity<>("Registration Successfull.", HttpStatus.OK);
    }
    
}
