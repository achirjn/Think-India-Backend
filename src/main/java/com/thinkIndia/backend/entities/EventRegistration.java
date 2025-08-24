package com.thinkIndia.backend.entities;

import com.thinkIndia.backend.entities.compositeKeys.EventRegistrationKey;
import com.thinkIndia.backend.helper.RandomStringGenerator;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class EventRegistration {
    @EmbeddedId
    private EventRegistrationKey id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne
    @MapsId("eventId")
    @JoinColumn(name="event_id")
    private Events event;

    private String teamName;
    private String teamId;

    
    public EventRegistration(User user, Events event, String teamId) {
        this.user = user;
        this.event = event;
        this.teamId = teamId;
    }

    public EventRegistration(Events event, User user, String teamName) {
        this.event = event;
        this.user = user;
        this.teamName = teamName;
        this.teamId = RandomStringGenerator.generateRandomString(12);
    }
}
