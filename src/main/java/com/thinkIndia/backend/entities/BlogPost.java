package com.thinkIndia.backend.entities;

import java.time.LocalDateTime;

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
public class BlogPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String imageUrl;
    private String heading;
    @Lob
    private String content;
    private LocalDateTime postTime;

    public BlogPost(String imageUrl, String heading, String content){
        this.imageUrl = imageUrl;
        this.heading = heading;
        this.content = content;
        this.postTime = LocalDateTime.now();
    }
}
