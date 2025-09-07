package com.thinkIndia.backend.entities;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Events {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;

    @ElementCollection
    @CollectionTable(name = "event_image_urls", joinColumns = @JoinColumn(name = "event_id"))
    @Column(name = "image_url", length = 512)
    private List<String> imageUrlList;
    @Lob
    private String details;
    @Lob
    private String message;
    private LocalDateTime dateTime;
    private int isActive;
    private int showEvent;

    @OneToMany(mappedBy="event")
    private List<EventRegistration> registrations;

    public Events(LocalDateTime dateTime, String details, List<String> imageUrlList, String message, String name, int isActive, int showEvent) {
        this.dateTime = dateTime;
        this.details = details;
        this.imageUrlList = imageUrlList;
        this.message = message;
        this.name = name;
        this.isActive = isActive;
        this.showEvent = showEvent;
    }
    
    public Events(String name, String details, String message, LocalDateTime dateTime,int isActive, int showEvent) {
        this.name = name;
        this.details = details;
        this.message = message;
        this.dateTime = dateTime;
        this.isActive = isActive;
        this.showEvent = showEvent;
    }
    
}
