package com.thinkIndia.backend.entities;

import jakarta.persistence.Column;
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
public class ResumeCV {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    
    private String name;

    @Lob
    @Column(columnDefinition="MEDIUMBLOB")
    private byte[] data;

    public ResumeCV(String name, byte[] data) {
        this.data = data;
        this.name = name;
    }
}
