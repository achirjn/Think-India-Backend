package com.thinkIndia.backend.dto;
import java.time.LocalDateTime;

import com.thinkIndia.backend.entities.Internship;
import com.thinkIndia.backend.entities.ResumeCV;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserInternshipsDto {
    private Internship internship;
    private String status;
    private LocalDateTime appliedDateTime;
    private ResumeCV resume;

    public UserInternshipsDto(LocalDateTime appliedDateTime, Internship internship, ResumeCV resume) {
        this.appliedDateTime = appliedDateTime;
        this.internship = internship;
        this.resume = resume;
    }
}
