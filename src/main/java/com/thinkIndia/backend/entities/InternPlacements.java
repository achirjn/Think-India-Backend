package com.thinkIndia.backend.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class InternPlacements {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    private String studentName;
    private String designation;
    private String role;
    private String instituteName;
    private String imageUrl;
    @Lob
    private String message;

    public InternPlacements(String designation, String imageUrl, String instituteName, String message, String role, String studentName) {
        this.designation = designation;
        this.imageUrl = imageUrl;
        this.instituteName = instituteName;
        this.message = message;
        this.role = role;
        this.studentName = studentName;
    }

    public InternPlacements(String designation, String imageUrl, String message, String studentName) {
        this.designation = designation;
        this.imageUrl = imageUrl;
        this.message = message;
        this.studentName = studentName;
    }
    
}
