package com.thinkIndia.backend.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Recommendations {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String email;
    private String message;
    private boolean resolved;
    private LocalDateTime postTime;

    public Recommendations(String name, String email, String message){
        this.name = name;
        this.email = email;
        this.message = message;
        this.resolved = false;
        this.postTime = LocalDateTime.now();
    }
}
