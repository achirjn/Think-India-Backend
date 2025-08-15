package com.thinkIndia.backend.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.thinkIndia.backend.entities.Images;
import com.thinkIndia.backend.entities.User;
import com.thinkIndia.backend.services.ImageService;
import com.thinkIndia.backend.services.UserService;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private ImageService imageService;
    @Autowired
    private UserService userService;

    @PostMapping("/uploadProfilePic/{email}")
    public ResponseEntity<?> uploadProfilePic(@RequestParam(value="Profile_pic") MultipartFile imageFile, @PathVariable("image") String email) throws IOException {
        
        User user = (User) userService.loadUserByUsername(email);
        int savedImageId = uploadImage(imageFile);
        user.setImageId(savedImageId);
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
