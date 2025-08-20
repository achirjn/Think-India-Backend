package com.thinkIndia.backend.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.thinkIndia.backend.entities.Images;
import com.thinkIndia.backend.entities.User;
import com.thinkIndia.backend.services.ImageService;
import com.thinkIndia.backend.services.UserService;



@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private ImageService imageService;
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/editProfile/{email}")
    public ResponseEntity<?> editProfile(@RequestParam(value="Profile_pic") MultipartFile imageFile,
    @RequestParam(value="UserName") String name, @PathVariable("image") String email) throws IOException {
        
        User user = (User) userService.loadUserByUsername(email);
        int savedImageId = uploadImage(imageFile);
        user.setImageId(savedImageId);
        user.setName(name);
        userService.saveUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PostMapping("/changePassword/{email}")
    public ResponseEntity<?> changePassword(@RequestParam(value="Old_password") String oldPassword,@RequestParam(value="New_password") String newPassword, @PathVariable("image") String email) {
        User user = (User) userService.loadUserByUsername(email);
        oldPassword = passwordEncoder.encode(oldPassword);
        if(!oldPassword.equals(user.getPassword())){
            return new ResponseEntity<>("wrong old password.",HttpStatus.BAD_REQUEST);
        }
        newPassword = passwordEncoder.encode(newPassword);
        user.setPassword(newPassword);
        userService.saveUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    public int uploadImage(MultipartFile imageFile) throws IOException{
        String imageName = StringUtils.cleanPath(imageFile.getOriginalFilename());
        Images image = new Images(imageName, imageFile.getBytes());
        Images savedImage = imageService.saveImage(image);
        if(savedImage==null) return -1;
        return savedImage.getId();
    }
}
