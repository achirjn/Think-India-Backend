package com.thinkIndia.backend.services;

import java.util.List;
import java.util.Optional;

import com.thinkIndia.backend.entities.TeamMember;

public interface TeamMemberService {

    public List<TeamMember> getMemberByPosition(String position);
    public void deleteMember(int id);
    public TeamMember saveMember(TeamMember member);
    public Optional<TeamMember> getById(int id);
    public List<TeamMember> getMembers();
}
