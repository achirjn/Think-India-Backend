package com.thinkIndia.backend.entities;

import java.time.LocalDateTime;

import com.thinkIndia.backend.entities.compositeKeys.InternApplicationKey;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class InternApplication {
    @EmbeddedId
    private InternApplicationKey id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne
    @MapsId("internshipId")
    @JoinColumn(name="internship_id")
    private Internship internship;

    private LocalDateTime applicationDateTime;
    
    @Column(nullable=true)
    private String status;

    //resume mapping;
    @OneToOne
    @JoinColumn(name="resume_id",referencedColumnName="id")
    private ResumeCV resumeCV;
    //constructors

    public InternApplication(User user, Internship internship, ResumeCV resumeCV) {
        this.user = user;
        this.internship = internship;
        this.applicationDateTime = LocalDateTime.now();
        this.resumeCV = resumeCV;
        this.status = "pending";
    }
}
