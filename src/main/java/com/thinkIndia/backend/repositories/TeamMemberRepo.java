package com.thinkIndia.backend.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.thinkIndia.backend.entities.TeamMember;


@Repository
public interface TeamMemberRepo extends JpaRepository<TeamMember, Integer>{

    public List<TeamMember> findByCommittee(String committee);
}
