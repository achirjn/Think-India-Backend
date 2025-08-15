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
public class TeamMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private int imageId;
    private String committee;
    private String position;
    public TeamMember(String name,int imageId, String committee,String position) {
        this.name = name;
        this.imageId = imageId;
        this.committee = committee;
        this.position = position;
    }
}
