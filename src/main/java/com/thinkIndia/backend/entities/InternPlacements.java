package com.thinkIndia.backend.entities;

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
public class InternPlacements {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    private String studentName;
    private String instituteName;
    private int imageId;

    public InternPlacements(String instituteName, String studentName, int imageId) {
        this.imageId = imageId;
        this.instituteName = instituteName;
        this.studentName = studentName;
    }

    public InternPlacements(int imageId) {
        this.imageId = imageId;
    }
}
