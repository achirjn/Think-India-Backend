package com.thinkIndia.backend.entities;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
    @Column(nullable = false, columnDefinition="TEXT")
    private String description;
    private String institute;
    private LocalDate startDate;
    @Column(nullable = true)
    private Integer duration;//in days
    @Column(columnDefinition="TEXT")
    private String eligiblity;
    private String imageUrl;
    @Column(nullable = false)
    private int isActive;

    @OneToMany(mappedBy="internship")
    private List<InternApplication> applications;

    public Internship(String role, String description, String institute, LocalDate startDate, Integer duration,
            String eligiblity,String imageUrl, int isActive) {
        this.role = role;
        this.description = description;
        this.institute = institute;
        this.startDate = startDate;
        this.duration = duration;
        this.eligiblity = eligiblity;
        this.imageUrl = imageUrl;
        this.isActive = isActive;
    }
}
