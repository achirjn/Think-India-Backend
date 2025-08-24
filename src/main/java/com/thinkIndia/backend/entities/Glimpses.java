package com.thinkIndia.backend.entities;

import java.time.LocalDate;

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
public class Glimpses {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private int imageId;
    private LocalDate date;
    
    public Glimpses(String name, int imageId) {
        this.name = name;
        this.imageId = imageId;
        this.date = LocalDate.now();
    }
}
