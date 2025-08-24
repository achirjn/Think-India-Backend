package com.thinkIndia.backend.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class User implements UserDetails{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String email;
    private String password;
    
    @Column(columnDefinition = "TINYINT(1)")
    private boolean adminPermit;

    private LocalDateTime lastActive;
    private int imageId;
    
    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="resume_id",referencedColumnName="id")
    private ResumeCV resumeCV;

    @OneToMany(mappedBy="user")
    private List<InternApplication> appliedInternships;

    @OneToMany(mappedBy="user")
    private List<EventRegistration> registeredEvents;
    

    public User(String name, String email, String password){
        this.name = name;
        this.email = email;
        this.password = password;
        this.adminPermit = false;
        this.imageId = -1;
        this.appliedInternships = new ArrayList<>();
    }
    public User(String name, String email,String password, boolean adminPermit) {
        this.adminPermit = adminPermit;
        this.email = email;
        this.name = name;
        this.password = password;
        this.imageId = -1;
        this.appliedInternships = new ArrayList<>();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.adminPermit) {
        return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"));
    } else {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }
    }

    @Override
    public String getUsername() {
        return this.email;
    }
}
