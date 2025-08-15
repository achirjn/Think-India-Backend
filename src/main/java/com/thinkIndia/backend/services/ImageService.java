package com.thinkIndia.backend.services;

import java.util.Optional;

import com.thinkIndia.backend.entities.Images;

public interface ImageService {
    public Images saveImage(Images image);
    public Optional<Images> getImageById(int id);
    public void deleteById(int id);
}
