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
public class InternApplicationKey implements Serializable{

    @Column(name="user_id")
    int userId;
    @Column(name="internship_id")
    int internshipId;

    @Override
    public boolean equals(Object obj) {
        if(this==obj) return true;
        if(!(obj instanceof InternApplicationKey)) return false;
        InternApplicationKey applicationKey = (InternApplicationKey) obj;
        return (this.userId==applicationKey.userId && this.internshipId==applicationKey.internshipId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, internshipId);
    }

}
