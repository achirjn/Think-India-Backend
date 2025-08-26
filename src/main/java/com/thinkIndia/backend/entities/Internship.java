package com.thinkIndia.backend.entities;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Internship {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String role;
    @Lob
    private String description;
    private String institute;
    private LocalDate startDate;
    private int duration;//in days
    private String eligiblity;
    private int isActive;

    @OneToMany(mappedBy="internship")
    private List<InternApplication> applications;

    public Internship(String role, String description, String institute, LocalDate startDate, int duration,
            String eligiblity, int isActive) {
        this.role = role;
        this.description = description;
        this.institute = institute;
        this.startDate = startDate;
        this.duration = duration;
        this.eligiblity = eligiblity;
        this.isActive = isActive;
    }
}
