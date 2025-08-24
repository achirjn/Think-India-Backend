package com.thinkIndia.backend.entities.compositeKeys;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EventRegistrationKey implements Serializable{

    @Column(name="user_id")
    int userId;
    @Column(name="event_id")
    int eventId;

    @Override
    public boolean equals(Object obj) {
        if(this==obj) return true;
        if(!(obj instanceof EventRegistrationKey)) return false;
        EventRegistrationKey registrationKey = (EventRegistrationKey) obj;
        return (this.userId==registrationKey.userId && this.eventId==registrationKey.eventId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, eventId);
    }
}
