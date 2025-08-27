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
    private int imageId;
    @Lob
    private String message;

    public InternPlacements(String designation, int imageId, String instituteName, String message, String role, String studentName) {
        this.designation = designation;
        this.imageId = imageId;
        this.instituteName = instituteName;
        this.message = message;
        this.role = role;
        this.studentName = studentName;
    }

    public InternPlacements(String designation, int imageId, String message, String studentName) {
        this.designation = designation;
        this.imageId = imageId;
        this.message = message;
        this.studentName = studentName;
    }
    
}
