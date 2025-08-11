package com.thinkIndia.backend.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RecommendationDto {

    private int recommendationId;
    private String name;
    private String email;
    private String message;
    private LocalDateTime postTime;

    public RecommendationDto(String name, String email, String message){
        this.name = name;
        this.email = email;
        this.message = message;
        this.postTime = LocalDateTime.now();
    }
    public RecommendationDto(String name, String email, String message, LocalDateTime dateTime){
        this.name = name;
        this.email = email;
        this.message = message;
        this.postTime = dateTime;
    }
}
