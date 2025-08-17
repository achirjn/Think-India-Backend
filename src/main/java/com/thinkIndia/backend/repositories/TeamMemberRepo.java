package com.thinkIndia.backend.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.thinkIndia.backend.entities.TeamMember;


@Repository
public interface TeamMemberRepo extends JpaRepository<TeamMember, Integer>{

    @Query(value="select * from team_member where position = ?1",nativeQuery=true)
    public List<TeamMember> getMemberByPosition(String position);
}
