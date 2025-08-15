package com.thinkIndia.backend.services.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thinkIndia.backend.entities.Images;
import com.thinkIndia.backend.repositories.ImageRepo;
import com.thinkIndia.backend.services.ImageService;

@Service
public class ImageServiceImpl implements ImageService{

    @Autowired
    private ImageRepo imageRepository;

    @Override
    public Images saveImage(Images image) {
        return imageRepository.save(image);
    }

    @Override
    public Optional<Images> getImageById(int id) {
        return imageRepository.findById(id);
    }

    @Override
    public void deleteById(int id) {
        imageRepository.deleteById(id);
    }

}
